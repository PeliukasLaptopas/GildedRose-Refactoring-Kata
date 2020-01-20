package com.gildedrose

import com.gildedrose.GildedRoseErrors.GildedRoseError.GildedError
import com.gildedrose.Items.{Item, ItemUpdateManager}

import scala.annotation.tailrec

class GildedRose(val initialItems: Vector[Item]) {
  def update(days: Int): Either[GildedError, Vector[Item]] = {
    @tailrec
    def go(daysLeft: Int, acc: Vector[Item]): Either[GildedError, Vector[Item]] = {
      if (daysLeft > 0) {
        ItemUpdateManager.updateItems(acc) match { //This is not written in "ItemUpdateManager.updateItems(acc).fold(<..>)" and instead in a 'match' because this needs to be a tailrec
          case Right(updatedItems) => go(daysLeft - 1, updatedItems)
          case err@ Left(_) => err
        }
      } else Right(acc)
    }

    go(days, initialItems)
  }
}
