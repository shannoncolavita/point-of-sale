package com.shoppingcart

import com.pointofsale.model.Item
import com.pointofsale.repository.ItemRepository
import org.mockito.Mockito._

trait TestEnvironment {

  val itemRepository: ItemRepository = mock(classOf[ItemRepository])

  val testItems: Seq[Item] = Seq(Item(1, "Apple", 0.6), Item(2, "Orange", 0.25))

  testItems.foreach(testItem => when(itemRepository.findItem(testItem.name)).thenReturn(Some(testItem)))
}
