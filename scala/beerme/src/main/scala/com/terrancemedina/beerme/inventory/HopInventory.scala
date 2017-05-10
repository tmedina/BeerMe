package com.terrancemedina.beerme.inventory

import akka.actor.{Actor, Props}
import com.terrancemedina.beerme.ingredients.{Hop, HopBill, HopTypes}
import com.terrancemedina.beerme.messages.HopCheckResponse

/**
  * Created by medinat on 5/8/17.
  */

class HopInventory extends Actor {

  case class HopBillAvailability(hop: Hop, quantity: Int, isAvailable: Boolean)

  override def receive = {
    case HopBill(hops) => sender ! checkStock(hops)
  }

  def checkStock(hops: List[(Hop, Int)]): HopCheckResponse = {
    Thread.sleep(2000)
    println("Hop checker done sleeping")
    val result = for {
      (hop, quantity) <- hops
    } yield HopBillAvailability(hop, quantity, HopInventory.inventory.contains(hop) && HopInventory.inventory(hop) - 1 > 0)

    if ((true /: result) ((p, q) => p && q.isAvailable)) {
      result.foreach(h => HopInventory.inventory(h.hop) = HopInventory.inventory(h.hop) - h.quantity)
      HopCheckResponse(true, List.empty)
    }
    else {
      HopCheckResponse(false, result.filter(h => !h.isAvailable) map (h => h.hop))
    }
  }
}

object HopInventory {
  val props = Props[HopInventory]
  val inventory = scala.collection.mutable.Map(
    Hop(HopTypes.Amarillo, 12.0, 6.5) -> 100,
    Hop(HopTypes.Cascade, 5.5, 6.5) -> 100
  )
}

