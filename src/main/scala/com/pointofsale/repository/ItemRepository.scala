package com.pointofsale.repository

import com.pointofsale.model.Item

trait ItemRepository {
  def findItem(itemName: String): Option[Item]
}

class StaticItemRepository extends ItemRepository {
  private val items = Seq(Item(1, "Apple", 0.6), Item(2, "Orange", 0.25))

  def findItem(itemName: String): Option[Item] =
    items.find(_.name.toLowerCase == itemName.toLowerCase)
}