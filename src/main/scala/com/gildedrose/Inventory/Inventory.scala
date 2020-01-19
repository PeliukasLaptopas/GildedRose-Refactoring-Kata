package com.gildedrose.Inventory

import com.gildedrose.Category.ItemCategoryManager._

object Inventory {
  /*There could be over +1000 items. Putting them for semantics into case object's doesn't sound reasonable.
    A viable solution could be to store them into some sort of a serialized format (maybe JSON)
    For now we are sticking to a simple Map, HOWEVER =>
    If we decide to switch to JSON - this implementation wouldn't need much of a change.*/
  val ITEMS: Map[Category, Vector[String]] =
    Map(
      Legendary -> Vector("Sulfuras, Hand of Ragnaros"),
      Conjured -> Vector("Conjured Mana Cake"),
      Common -> Vector("Elixir of the Mongoose", "+5 Dexterity Vest"),
      BackStagePass -> Vector("Backstage passes to a TAFKAL80ETC concert"),
      Aged -> Vector("Aged Brie")
    )
}
