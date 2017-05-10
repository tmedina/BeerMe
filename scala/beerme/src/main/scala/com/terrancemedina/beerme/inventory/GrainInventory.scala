package com.terrancemedina.beerme.inventory

import akka.actor.{Actor, Props}
import com.terrancemedina.beerme.ingredients.{Grain, GrainBill, GrainTypes}
import com.terrancemedina.beerme.messages.GrainCheckResponse

/**
  * Created by medinat on 5/9/17.
  */
class GrainInventory extends Actor {

  case class GrainBillAvailability(grain: Grain, quantity: Int, isAvailable: Boolean)

  override def receive = {
    case GrainBill(grains) => sender ! checkStock(grains)
  }

  def checkStock(grains: List[(Grain, Int)]): GrainCheckResponse = {
    Thread.sleep(3000)
    println("Grain checker done sleeping")
    val availability = for (
      (grain, quantity) <- grains
    ) yield GrainBillAvailability(
      grain,
      quantity,
      GrainInventory.inventory.contains(grain) && GrainInventory.inventory(grain) - quantity > 0)

    if ((true /: (availability)) ((i, j) => i && j.isAvailable)) {
      availability.foreach(g => GrainInventory.inventory(g.grain) = GrainInventory.inventory(g.grain) - g.quantity)
      GrainCheckResponse(true, List.empty)
    }
    else {
      GrainCheckResponse(false, availability.filter(g => !g.isAvailable).map(g => g.grain))
    }
  }
}

object GrainInventory {
  val props = Props[GrainInventory]
  val inventory = scala.collection.mutable.Map(
    Grain(GrainTypes.Barley) -> 0,
    Grain(GrainTypes.Corn) -> 100,
    Grain(GrainTypes.Oatmeal) -> 100,
    Grain(GrainTypes.Rice) -> 100,
    Grain(GrainTypes.Rye) -> 0,
    Grain(GrainTypes.Wheat) -> 100
  )
}

