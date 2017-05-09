package com.pointofsale.utilities

import scala.math.BigDecimal.RoundingMode

object RoundAmount {
  def apply(amount: BigDecimal) = amount.setScale(2, RoundingMode.HALF_EVEN)
}