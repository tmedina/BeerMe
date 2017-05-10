package com.terrancemedina.beerme.ingredients

import com.terrancemedina.beerme.ingredients.HopTypes.HopType

/**
  * Created by medinat on 5/6/17.
  */
object HopTypes extends Enumeration {
  type HopType = Value
  val Amarillo, Cascade, Centennial, Chinook, Fuggle, Perle, Saaz, Hallertau, Golding = Value
}

case class Hop(hopType: HopType, alphaAcidity: Double, betaAcidity: Double)

case class HopBill(hops: List[(Hop, Int)])
