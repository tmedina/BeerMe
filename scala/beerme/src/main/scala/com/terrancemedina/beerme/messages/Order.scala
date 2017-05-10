package com.terrancemedina.beerme.messages

import com.terrancemedina.beerme.ingredients.{GrainBill, HopBill}

/**
  * Created by medinat on 5/6/17.
  */

case class Order(grainBill: GrainBill, hopsBill: HopBill)
