package com.gildedrose

object ItemsClassifier {
  sealed trait Category
  case object Conjured extends Category
  case object Legendary extends Category
  case object Common extends Category
  case object BackStagePass extends Category

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