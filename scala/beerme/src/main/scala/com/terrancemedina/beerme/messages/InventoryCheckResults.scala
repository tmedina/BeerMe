package com.terrancemedina.beerme.messages

import com.terrancemedina.beerme.ingredients.{Grain, Hop}

/**
  * Created by medinat on 5/9/17.
  */

case class InventoryResponse(hasGrains: GrainCheckResponse, hasHops: HopCheckResponse)

case class GrainCheckResponse(canFulfill: Boolean, missingGrains: List[Grain])

case class HopCheckResponse(canFulfill: Boolean, missingHops: List[Hop])
