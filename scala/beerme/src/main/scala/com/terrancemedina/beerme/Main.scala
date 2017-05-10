package com.terrancemedina.beerme

import akka.actor.ActorSystem
import com.terrancemedina.beerme.ingredients._
import com.terrancemedina.beerme.messages.Order
import com.terrancemedina.beerme.supervisors.OrderSupervisor

/**
  * Created by medinat on 5/6/17.
  */
object Main extends App {
  val system = ActorSystem.create("BeerMe")

  val wheat = Grain(GrainTypes.Wheat)
  val barley = Grain(GrainTypes.Barley)
  val rye = Grain(GrainTypes.Rye)
  val amarillo = Hop(HopTypes.Amarillo, 11.0, 6.5)
  val cascade = Hop(HopTypes.Cascade, 5.5, 6.5)

  val orderSupervisor = system.actorOf(OrderSupervisor.props, "orderSupervisor")

  val order = Order(
    GrainBill((wheat, 5) :: (barley, 6) :: (rye, 7) :: Nil),
    HopBill((amarillo, 3) :: (cascade, 8) :: Nil))

  orderSupervisor ! order

  system.awaitTermination()
}
