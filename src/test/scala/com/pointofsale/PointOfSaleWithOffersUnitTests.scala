package com.pointofsale

import com.pointofsale.utilities.RoundAmount
import org.mockito.Mockito._
import org.scalatest.{FeatureSpec, GivenWhenThen}

class PointOfSaleWithOffersUnitTests extends FeatureSpec with GivenWhenThen with TestEnvironment {

  feature("The point of sale system adds up the prices correctly, with offers") {

    when(offerRepository.getActiveOffers(testItems.head)).thenReturn(Some(buyOneGetOneFreeTestOffer))
    when(offerRepository.getActiveOffers(testItems.tail.head)).thenReturn(Some(buyTwoGetOneFreeTestOffer))

    scenario("One item but does not not qualify for offer") {
      Given("There is one item scanned")
      val testItem = testItems.head
      val cart = PointOfSale(Seq(testItem.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem.price)}"
      assertResult(expected)(result)
    }

    scenario("Two of the same items, price should be for 1") {
      Given("There are two items scanned")
      val testItem = testItems.head
      val cart = PointOfSale(Seq(testItem.name, testItem.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem.price)}"
      assertResult(expected)(result)
    }

    scenario("Two different items, minimum quantities are not met so they do not qualify for the offer") {
      Given("There are two items scanned")
      val testItem1 = testItems.head
      val testItem2 = testItems.tail.head
      val cart = PointOfSale(Seq(testItem1.name, testItem2.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem1.price + testItem2.price)}"
      assertResult(expected)(result)
    }

    scenario("Two different items with multiple quantities, both qualify for the offer") {
      Given("There are multiple items scanned")
      val testItem1 = testItems.head
      val testItem2 = testItems.tail.head
      val cart = PointOfSale(Seq(testItem1.name, testItem2.name, testItem2.name, testItem1.name, testItem2.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem1.price + (testItem2.price * 2))}"
      assertResult(expected)(result)
    }

    scenario("Two different items with multiple quantities, both qualify for the offer, with remainders") {
      Given("There are multiple items scanned")
      val testItem1 = testItems.head
      val testItem2 = testItems.tail.head
      val cart = PointOfSale(Seq(testItem1.name, testItem1.name, testItem2.name, testItem2.name, testItem2.name, testItem1.name, testItem2.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount((testItem1.price * 2) + (testItem2.price * 3))}"
      assertResult(expected)(result)
    }
  }
}