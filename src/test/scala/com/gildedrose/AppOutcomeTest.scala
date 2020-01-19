package com.gildedrose

import com.gildedrose.GildedRoseErrors.GildedRoseError.GildedError
import org.scalatest._
import cats.syntax.either._

class AppOutcomeTest  extends FunSpec with Matchers {
  describe("app.play()") {
    it("should successfully return updated items") {
      val DAYS = 10

      val itemsBeforeUpdate = Vector[Item](
        Item("+5 Dexterity Vest", 10, 20),
        Item("Aged Brie", 2, 0),
        Item("Elixir of the Mongoose", 5, 7),
        Item("Sulfuras, Hand of Ragnaros", 0, 80),
        Item("Sulfuras, Hand of Ragnaros", 1, 80),
        Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
        Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
        Item("Conjured Mana Cake", 3, 6))

      val itemsAfterUpdate: Either[GildedError, Vector[Item]] = Vector[Item](
        Item("+5 Dexterity Vest", 0, 10),
        Item("Aged Brie", 0, 2),
        Item("Elixir of the Mongoose", 0, 0),
        Item("Sulfuras, Hand of Ragnaros", 0, 80),
        Item("Sulfuras, Hand of Ragnaros", 1, 80),
        Item("Backstage passes to a TAFKAL80ETC concert", 5, 35),
        Item("Backstage passes to a TAFKAL80ETC concert", 0, 50),
        Item("Backstage passes to a TAFKAL80ETC concert", 0, 0),
        Item("Conjured Mana Cake", 0, 0)).asRight

      val app = new GildedRose(itemsBeforeUpdate)
      val outcome = app.play(DAYS)

      outcome shouldBe itemsAfterUpdate
    }
  }

  describe("app.play()") {
    it("should fail when an unknown Item is passed") {
      val DAYS = 10

      val itemsBeforeUpdate = Vector[Item](
        Item("+5 Dexterity Vest", 10, 20), //This Item is known
        Item("Turn 4 lethal?", 2, 0))

      val app = new GildedRose(itemsBeforeUpdate)
      val outcome = app.play(DAYS)

      outcome shouldBe 'left
    }
  }

  describe("app.play()") {
    it("should fail when an Item with negative SellIn or Quality is passed") {
      val DAYS = 10

      val itemsBeforeUpdate = Vector[Item](
        Item("+5 Dexterity Vest", -10, 20))

      val app = new GildedRose(itemsBeforeUpdate)
      val outcome = app.play(DAYS)

      outcome shouldBe 'left
    }
  }
}