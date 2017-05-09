package com.pointofsale.model

case class Offer(description: String, minimumQuantity: Int, multiplier: Int) {
  def calculateTotalPrice(totalItems: Int, itemPrice: BigDecimal): BigDecimal = {
    val remainder = totalItems % minimumQuantity
    (itemPrice * remainder) + ((totalItems - remainder) / minimumQuantity) * multiplier * itemPrice}
}

