package com.pointofsale

import com.pointofsale.repository.{StaticItemRepository, StaticOfferRepository}

object Main extends App {

  private val itemRepository = new StaticItemRepository
  private val offerRepository = new StaticOfferRepository

  private val scannedItemNames = args

  private val pointOfSale = PointOfSale(scannedItemNames, itemRepository, offerRepository)

  println(pointOfSale.checkout)
}
