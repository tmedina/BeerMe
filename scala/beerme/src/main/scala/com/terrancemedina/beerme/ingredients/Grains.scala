package com.terrancemedina.beerme.ingredients

import com.terrancemedina.beerme.ingredients.GrainTypes.GrainType

/**
  * Created by medinat on 5/6/17.
  */
object GrainTypes extends Enumeration {
  type GrainType = Value
  val Wheat, Barley, Rye, Oatmeal, Corn, Rice = Value
}

case class Grain(grainType: GrainType)

case class GrainBill(grains: List[(Grain, Int)])
