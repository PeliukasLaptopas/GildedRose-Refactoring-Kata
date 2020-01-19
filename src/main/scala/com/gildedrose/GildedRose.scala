package com.gildedrose

import ItemsClassifier._
import GildedRose._
import cats.data.ValidatedNel
import com.gildedrose.GildedRoseErrors.GildedRoseError.{CouldNotUpdateItemError, GildedError}
import cats.instances.either._
import cats.instances.vector._
import cats.syntax.traverse._
import annotation.tailrec

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
    if (newQuality <= threshold)
      newQuality
    else
      threshold
  }
}

class GildedRose(val initialItems: Vector[Item]) {
  def play(days: Int): Either[GildedError, Vector[Item]] = {
    @tailrec
    def go(daysLeft: Int, acc: Vector[Item]): Either[GildedError, Vector[Item]] = {
      if (daysLeft > 0) {
        updateItems(acc) match { //This is not written in "updateQuality(acc).fold(<..>)" and instead in a 'match' because this needs to be a tailrec
          case Right(updatedItems) => go(daysLeft - 1, updatedItems)
          case err@ Left(_) => err
        }
      } else Right(acc)
    }

    go(days, initialItems)
  }

  private def updateItems(items: Vector[Item]): Either[GildedError, Vector[Item]] = {
    val maybeValidItems = items.map(itemIsValid).sequence
    val maybeUpdatedItems = items.map(updateItem).sequence

    for {
      validAreItems <- maybeValidItems
      updatedItems <- maybeUpdatedItems
    } yield updatedItems
  }

  val updateItem: Item => Either[GildedError, Item] = { case item @ Item(name, sellIn, quality) =>
    classifyItem(item).right.map {
        case Common => Item(name, decrease(sellIn, 1), Common.updateQuality(sellIn, quality))
        case Conjured => Item(name, decrease(sellIn, 1), Conjured.updateQuality(sellIn, quality))
        case BackStagePass => Item(name, decrease(sellIn, 1), BackStagePass.updateQuality(sellIn, quality))
        case Legendary => item
      }
    }
}
