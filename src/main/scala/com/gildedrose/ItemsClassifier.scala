package com.gildedrose

import GildedRose._

object ItemsClassifier {
  trait Category
  case object Legendary extends Category
  case object Conjured extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int = {
      val by = if (sellIn <= 0) 4 else 2
      decrease(quality, by)
    }
  }
  case object Common extends Category {
    def updateQuality(quality: Int): Int = decrease(quality, 1)
  }
  case object BackStagePass extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int = {
      val by =
        if (sellIn > 10) Some(1) else
        if (sellIn <= 10 && sellIn >= 5) Some(2) else
        if (sellIn <= 5 && sellIn > 0) Some(3) else None

      by.fold(0)(by => increase(quality, by, 50))
    }
  }
  /*
      item.copy(name, decrease(sellIn, by), decrease(quality, 1))*/

  /*There could be over +1000 items. Putting them into case object's doesn't sound reasonable.
    A viable solution could be to store them into some sort of a serialized format (maybe JSON)
    If we decide to do so - this implementation wouldn't need much of a change.
    For now we are sticking to a simple Map*/
  val ITEMS: Map[Category, Vector[String]] = {
    Map(
      Legendary -> Vector("Sulfuras, Hand of Ragnaros"),
      Conjured -> Vector("Conjured Mana Cake"),
      Common -> Vector("Aged Brie", "Elixir of the Mongoose", "+5 Dexterity Vest"),
      BackStagePass -> Vector("Backstage passes to a TAFKAL80ETC concert")
    )
  }
}