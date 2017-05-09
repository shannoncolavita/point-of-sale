package com.pointofsale

import com.pointofsale.repository.{ItemRepository, OfferRepository}
import com.pointofsale.utilities.RoundAmount

case class PointOfSale(scannedItemNames: Seq[String], itemRepository: ItemRepository, offerRepository: OfferRepository) {
  def checkout: String = {
    val items = scannedItemNames.map(scannedItemName => itemRepository.findItem(scannedItemName)
      .getOrElse(throw new IllegalArgumentException(s"Item $scannedItemName not found.")))

    val total = items.groupBy(_.name).map{ case (itemName, allItems) =>
      offerRepository.getActiveOffers(allItems.head) match {
        case Some(offer) => offer.calculateTotalPrice(allItems.size, allItems.head.price)
        case None => allItems.size * allItems.head.price
      }
    }.sum
    s"£${RoundAmount(total)}"
  }
}

