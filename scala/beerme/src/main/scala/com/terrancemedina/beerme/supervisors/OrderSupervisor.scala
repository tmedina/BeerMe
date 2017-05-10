package com.terrancemedina.beerme.supervisors

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.terrancemedina.beerme.messages._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by medinat on 5/6/17.
  */

class OrderSupervisor extends Actor {

  val warehouseSupervisor = context.actorOf(WarehouseSupervisor.props)

  override def receive = {
    case o: Order => this.processOrder(o)
  }

  def processOrder(o: Order) = {
    implicit val timeout = Timeout(50 seconds)

    // Check availability of each ingredient with the WarehouseSupervisor

    val grainCheckRequest = warehouseSupervisor ? o.grainBill
    val hopsCheckRequest = warehouseSupervisor ? o.hopsBill

    (for {
      grainCheckResponse <- grainCheckRequest.mapTo[GrainCheckResponse]
      hopsCheckResponse <- hopsCheckRequest.mapTo[HopCheckResponse]
    } yield InventoryResponse(grainCheckResponse, hopsCheckResponse))
      .onComplete {

        case Success(inventory) => inventory match {

          case InventoryResponse(
          GrainCheckResponse(true, _),
          HopCheckResponse(true, _)) => println("We're good to go!")

          case InventoryResponse(
          GrainCheckResponse(true, _),
          HopCheckResponse(false, missingHops)) => {
            val missingHopList = ("" /: missingHops) ((i, j) => i + j.hopType + ", ")
            println("We need more " + missingHopList + " Hops.")
          }

          case InventoryResponse(
          GrainCheckResponse(false, missingGrains),
          HopCheckResponse(true, _)) => {
            val missingGrainList = ("" /: missingGrains) ((i, j) => i + j.grainType + ", ")
            println("We need more " + missingGrainList)
          }

          case InventoryResponse(
          GrainCheckResponse(false, missingGrains),
          HopCheckResponse(false, missingHops)) => {
            val missingHopList = ("" /: missingHops) ((i, j) => i + ", " + j.hopType)
            val missingGrainList = ("" /: missingGrains) ((i, j) => i + ", " + j.grainType)
            println("We've got nothing!")
            println("We need more " + missingHopList + " Hops.")
            println("We also need more " + missingGrainList)
          }
        }
        case Failure(ex) => ex.printStackTrace
      }

    // Pass order to the BrewSupervisor


    // TODO: should I have Main send a PoisonPill after all orders instead?
    context.system.shutdown()
  }
}

object OrderSupervisor {
  val props = Props[OrderSupervisor]
}
