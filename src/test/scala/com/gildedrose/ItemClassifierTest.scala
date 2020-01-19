package com.gildedrose

import com.gildedrose.Category.ItemCategoryManager
import com.gildedrose.Items.Item
import org.scalatest._

class ItemClassifierTest extends FunSpec with Matchers {
  describe("itemIsValid()") {
    it("should return Left when Item with negative SellIn or Quality is given") {
      val item = Item("Arcane shot face turn 1", 10, -10)
      ItemCategoryManager.itemFieldsAreValid(item) shouldBe 'left
    }
  }

  describe("itemIsValid()") {
    it("should return Right when correct SellIn and Quality is given") {
      val item = Item("Arcane shot face turn 1", 10, 10)
      ItemCategoryManager.itemFieldsAreValid(item) shouldBe 'right
    }
  }
}