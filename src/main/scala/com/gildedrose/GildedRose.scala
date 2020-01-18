package com.gildedrose

import GildedRose._

object GildedRose {
  sealed trait Category
  case object Conjured extends Category
  case object Legendary extends Category
  case object Common extends Category
  case object BackStagePass extends Category

  val ITEMS: Map[Category, Vector[String]] = {
    Map(
      Legendary -> Vector("Sulfuras, Hand of Ragnaros"),
      Conjured -> Vector("Conjured Mana Cake"),
      Common -> Vector("Aged Brie", "Elixir of the Mongoose", "+5 Dexterity Vest"),
      BackStagePass -> Vector("Backstage passes to a TAFKAL80ETC concert")
    )
  }
}

class GildedRose(val items: Vector[Item]) {

  def updateQuality(): Unit = {
    items.map {
      updateCommon orElse
      updateLegendary orElse
      updateConjured
    }
  }

  val updateCommon: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(Common).contains(name) => Item(name, quality - 1, sellIn - 1)
  }

  val updateLegendary: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(Legendary).contains(name) => Item(name, quality, sellIn)
  }

  val updateConjured: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(Conjured).contains(name) => Item(name, quality, sellIn)
  }

  val updateBackStagePass: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(BackStagePass).contains(name) => Item(name, quality, sellIn)
  }
}