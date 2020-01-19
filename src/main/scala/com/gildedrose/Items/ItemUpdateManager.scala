package com.gildedrose.Items

import com.gildedrose.Category.ItemCategoryManager._
import com.gildedrose.GildedRoseErrors.GildedRoseError.GildedError
import cats.instances.either._
import cats.instances.vector._
import cats.syntax.traverse._
import com.gildedrose.ItemUtils.ItemFieldsManager._

object ItemUpdateManager {
  def updateItems(items: Vector[Item]): Either[GildedError, Vector[Item]] = {
    val maybeValidItems = items.map(itemFieldsAreValid).sequence
    val maybeUpdatedItems = items.map(updateItem).sequence

    for {
      _ <- maybeValidItems
      updatedItems <- maybeUpdatedItems
    } yield updatedItems
  }

  val updateItem: Item => Either[GildedError, Item] = { case item @ Item(name, sellIn, quality) =>
    classifyItem(item).right.map {
      case Common => Item(name, decrease(sellIn, 1), Common.updateQuality(sellIn, quality))
      case Conjured => Item(name, decrease(sellIn, 1), Conjured.updateQuality(sellIn, quality))
      case BackStagePass => Item(name, decrease(sellIn, 1), BackStagePass.updateQuality(sellIn, quality))
      case Legendary => item
      case Aged => Item(name, decrease(sellIn, 1), Aged.updateQuality(sellIn, quality))
    }
  }
}
