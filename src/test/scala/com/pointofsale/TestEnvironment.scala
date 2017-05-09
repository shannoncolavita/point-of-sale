package com.pointofsale

import com.pointofsale.model.{Item, Offer}
import com.pointofsale.repository.{ItemRepository, OfferRepository}
import org.mockito.Mockito._

trait TestEnvironment {

  val itemRepository: ItemRepository = mock(classOf[ItemRepository])
  val offerRepository: OfferRepository = mock(classOf[OfferRepository])

  val testItems: Seq[Item] = Seq(Item(1, "Apple", 0.6), Item(2, "Orange", 0.25))

  val buyOneGetOneFreeTestOffer = Offer("BuyOneGetOneFreeOffer", 2, 1)

  val buyTwoGetOneFreeTestOffer = Offer("BuyOneGetOneFreeOffer", 3, 2)

  testItems.foreach(testItem => when(itemRepository.findItem(testItem.name)).thenReturn(Some(testItem)))
}
