package com.pointofsale

import com.pointofsale.model.Item
import com.pointofsale.utilities.RoundAmount
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.{FeatureSpec, GivenWhenThen}

class PointOfSaleUnitTests extends FeatureSpec with GivenWhenThen with TestEnvironment {

  feature("The point of sale system adds up the prices correctly, without any offers") {

    when(offerRepository.getActiveOffers(any(classOf[Item]))).thenReturn(None)

    scenario("One item") {
      Given("There is one item scanned")
      val testItem = testItems.head
      val cart = PointOfSale(Seq(testItem.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem.price)}"
      assertResult(expected)(result)
    }

    scenario("Two of the same items") {
      Given("There are two items scanned")
      val testItem = testItems.head
      val cart = PointOfSale(Seq(testItem.name, testItem.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount(testItem.price * 2)}"
      assertResult(expected)(result)
    }

    scenario("Two different items") {
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

    scenario("Two different items with multiple quantities") {
      Given("There are multiple items scanned")
      val testItem1 = testItems.head
      val testItem2 = testItems.tail.head
      val cart = PointOfSale(Seq(testItem1.name, testItem2.name, testItem2.name, testItem1.name, testItem2.name), itemRepository, offerRepository)

      When("The checkout method is invoked")
      val result = cart.checkout

      Then("A string is returned in the expected format with the correct total")
      val expected = s"£${RoundAmount((testItem1.price * 2) + (testItem2.price * 3))}"
      assertResult(expected)(result)
    }
  }
}