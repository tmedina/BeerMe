package com.terrancemedina.beerme.supervisors

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import com.terrancemedina.beerme.ingredients._
import com.terrancemedina.beerme.inventory.{GrainInventory, HopInventory}
import com.terrancemedina.beerme.messages.{GrainCheckResponse, HopCheckResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.language.postfixOps

/**
  * Created by medinat on 5/6/17.
  */

class WarehouseSupervisor extends Actor {

  override def receive = {
    case g: GrainBill => {
      println("Checking the grain stocks...")
      checkStockGrains(g, sender)
    }
    case h: HopBill => {
      println("Checking the hop stocks...")
      checkStockHops(h, sender)
    }
    case _ => sender ! false
  }

  def checkStockHops(hops: HopBill, sender: ActorRef) = {
    val inventory = context.actorOf(HopInventory.props, "HopInventory")
    implicit val timeout = Timeout(50 seconds)
    val hasHopsF = inventory ? hops
    hasHopsF.onComplete {
      case Success(value) => {
        sender ! value
      }
      case Failure(ex) => {
        ex.printStackTrace()
        sender ! HopCheckResponse(false, List.empty)
      }
    }
  }

  def checkStockGrains(grains: GrainBill, sender: ActorRef) = {
    val inventory = context.actorOf(GrainInventory.props, "GrainInventory")
    implicit val timeout = Timeout(50 seconds)
    val hasGrainsF = inventory ? grains
    hasGrainsF.onComplete {
      case Success(value) => sender ! value
      case Failure(ex) => {
        ex.printStackTrace
        sender ! GrainCheckResponse(false, List.empty)
      }
    }
  }
}

object WarehouseSupervisor {
  val props = Props[WarehouseSupervisor]
}
