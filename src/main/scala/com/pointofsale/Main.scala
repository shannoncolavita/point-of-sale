package com.pointofsale

import com.pointofsale.repository.StaticItemRepository

object Main extends App {

  private val itemRepository = new StaticItemRepository

  private val scannedItemNames = args

  private val pointOfSale = PointOfSale(scannedItemNames, itemRepository)

  println(pointOfSale.checkout)
}
