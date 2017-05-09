package com.pointofsale.repository

import com.pointofsale.model.{Item, Offer}

trait OfferRepository {
  def getActiveOffers(item: Item): Option[Offer]
}

class StaticOfferRepository extends OfferRepository {

  private val buyOneGetOneFreeOffer = Offer("BuyOneGetOneFreeOffer", 2, 1)

  private val buyTwoGetOneFreeOffer = Offer("BuyOneGetOneFreeOffer", 3, 2)

  def getActiveOffers(item: Item): Option[Offer] = item.name match {
    case "Apple" => Some(buyOneGetOneFreeOffer)
    case "Orange" => Some(buyTwoGetOneFreeOffer)
    case _ => None
  }
}
