package com.gildedrose

import com.gildedrose.GildedRoseErrors.GildedRoseError.GildedError
import org.scalatest._
import cats.syntax.either._
import com.gildedrose.Items.{Item, ItemUpdateManager}

class ItemUpdateManagerTest  extends FunSpec with Matchers {
  describe("UpdateItem()") {
    it("should correctly update a common Item") {
      val commonItemBeforeUpdate = Item("+5 Dexterity Vest", 5, 20)
      val commonItemAfterUpdate: Either[GildedError, Item] =  Item("+5 Dexterity Vest", 4, 19).asRight

      val commonUpdatedItem = ItemUpdateManager.updateItem(commonItemBeforeUpdate)

      commonUpdatedItem shouldBe commonItemAfterUpdate
    }


    describe("UpdateItem()") {
      it("should correctly update a Conjured Item") {
        val conjuredItemBeforeUpdate = Item("Conjured Mana Cake", 5, 20)
        val conjuredItemAfterUpdate: Either[GildedError, Item] =  Item("Conjured Mana Cake", 4, 18).asRight

        val conjuredUpdatedItem = ItemUpdateManager.updateItem(conjuredItemBeforeUpdate)

        conjuredUpdatedItem shouldBe conjuredItemAfterUpdate
      }
    }

    describe("UpdateItem()") {
      it("should correctly update a BackStagePass Item") {
        val backStageItemBeforeUpdate = Item("Backstage passes to a TAFKAL80ETC concert", 15, 20)
        val backStageItemAfterUpdate: Either[GildedError, Item] =  Item("Backstage passes to a TAFKAL80ETC concert", 14, 21).asRight

        val backStagedUpdatedItem = ItemUpdateManager.updateItem(backStageItemBeforeUpdate)

        backStagedUpdatedItem shouldBe backStageItemAfterUpdate
      }
    }

    describe("UpdateItem()") {
      it("should correctly update a Legendary Item") {
        val legendaryItemBeforeUpdate = Item("Sulfuras, Hand of Ragnaros", 5, 20)
        val legendaryItemAfterUpdate: Either[GildedError, Item] =  Item("Sulfuras, Hand of Ragnaros", 5, 20).asRight

        val legendarUpdatedItem = ItemUpdateManager.updateItem(legendaryItemBeforeUpdate)

        legendarUpdatedItem shouldBe legendaryItemAfterUpdate
      }
    }

    describe("UpdateItem()") {
      it("should correctly update a Aged Item") {
        val agedItemBeforeUpdate = Item("Aged Brie", 5, 20)
        val agedItemAfterUpdate: Either[GildedError, Item] =  Item("Aged Brie", 4, 21).asRight

        val agedUpdatedItem = ItemUpdateManager.updateItem(agedItemBeforeUpdate)

        agedUpdatedItem shouldBe agedItemAfterUpdate
      }
    }
  }
}