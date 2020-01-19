package com.gildedrose

import org.scalatest._

class ItemClassifierTest extends FunSpec with Matchers {
  describe("itemIsValid()") {
    it("should return Left when Item with negative SellIn or Quality is given") {
      val item = Item("Arcane shot face turn 1", 10, -10)
      ItemsClassifier.itemFieldsAreValid(item) shouldBe 'left
    }
  }

  describe("itemIsValid()") {
    it("should return Right when correct SellIn and Quality is given") {
      val item = Item("Arcane shot face turn 1", 10, 10)
      ItemsClassifier.itemFieldsAreValid(item) shouldBe 'right
    }
  }
}