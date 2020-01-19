package com.gildedrose

import ItemsClassifier._
import GildedRose._

object GildedRose {
  //Quality or sellIn will never be negative
  def decrease(value: Int, by: Int) = {
    if (value > by)
      value - by
    else
      0
  }

  def increase(value: Int, by: Int, threshold: Int) = {
    val newQuality = value + by
    if (value + by <= threshold)
      newQuality
    else
      threshold
  }
}

/*We can use Partial functions .isDefined to represent which Item is
  being iterated since we can not modify Item class to extend anything*/
class GildedRose(val items: Vector[Item]) {
  def updateQuality(): Unit = {
    items.map {
      updateCommon orElse
      updateLegendary orElse
      updateConjured orElse
      updateBackStagePass
    }
  }

  private val updateCommon: PartialFunction[Item, Item] = {
    case item @ Item(name, sellIn, quality) if ITEMS(Common).contains(name) =>
      item.copy(name, decrease(sellIn, 1), Common.updateQuality(quality))
  }

  private val updateConjured: PartialFunction[Item, Item] = {
    case item @Item(name, sellIn, quality) if ITEMS(Conjured).contains(name) =>
      val by = if (sellIn <= 0) 4 else 2
      item.copy(name, decrease(sellIn, 1), Conjured.updateQuality(quality, by))
  }

  private val updateBackStagePass: PartialFunction[Item, Item] = {
    case Item(name, sellIn, quality) if ITEMS(BackStagePass).contains(name) =>
      Item(name, decrease(sellIn, 1), BackStagePass.updateQuality(sellIn, quality))
  }

  private val updateLegendary: PartialFunction[Item, Item] = {
    case item: Item if ITEMS(Legendary).contains(item.name) => item
  }
}
