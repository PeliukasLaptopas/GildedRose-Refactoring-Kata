package com.gildedrose

import ItemsClassifier._

object GildedRose {
  private def decrease(value: Int, amount: Int) = {
    if (value > amount)
      value - amount
    else
      0
  }

  private def increase(value: Int, amount: Int, threshold: Int) = {
    val newQuality = value + amount
    if (value + amount <= threshold)
      newQuality
    else
      threshold
  }
}

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
    case Item(name, quality, sellIn) if ITEMS(Common).contains(name) => Item(name, quality - 1, sellIn - 1)
  }

  private val updateLegendary: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(Legendary).contains(name) => Item(name, quality, sellIn)
  }

  private val updateConjured: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(Conjured).contains(name) => Item(name, quality, sellIn)
  }

  private val updateBackStagePass: PartialFunction[Item, Item] = {
    case Item(name, quality, sellIn) if ITEMS(BackStagePass).contains(name) => Item(name, quality, sellIn)
  }
}
