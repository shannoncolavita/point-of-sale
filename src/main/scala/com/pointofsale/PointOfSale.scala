package com.pointofsale

import com.pointofsale.repository.ItemRepository
import com.pointofsale.utilities.RoundAmount

case class PointOfSale(scannedItemNames: Seq[String], itemRepository: ItemRepository) {
  def checkout: String = {
    val items = scannedItemNames.map(scannedItemName => itemRepository.findItem(scannedItemName)
      .getOrElse(throw new IllegalArgumentException(s"Item $scannedItemName not found.")))
    val total = RoundAmount(items.map(_.price).sum)
    s"£$total"
  }
}

