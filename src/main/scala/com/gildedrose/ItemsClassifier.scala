package com.gildedrose

import GildedRose._
import com.gildedrose.GildedRoseErrors.GildedRoseError.{GildedError, NegativeItemFieldError, Quality, SellIn, UnknownItemCategoryError}
import com.gildedrose.ItemsClassifier.Aged

object ItemsClassifier {
  trait Category
  case object Aged extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int = {
      val by = if (sellIn > 0) 1 else 0
      increase(quality, by, 50)
    }
  }
  case object Legendary extends Category
  case object Conjured extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int = {
      val by = if (sellIn <= 0) 4 else 2
      decrease(quality, by)
    }
  }
  case object Common extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int =  decrease(quality, 1)
  }
  case object BackStagePass extends Category {
    def updateQuality(sellIn: Int, quality: Int): Int = {
      val by =
        if (sellIn > 10) Some(1) else
        if (sellIn <= 10 && sellIn > 5) Some(2) else
        if (sellIn <= 5 && sellIn >= 1) Some(3) else None

      by.fold(0)(by => increase(quality, by, 50))
    }
  }

  /*There could be over +1000 items. Putting them for semantics into case object's doesn't sound reasonable.
    A viable solution could be to store them into some sort of a serialized format (maybe JSON)
    For now we are sticking to a simple Map, HOWEVER =>
    If we decide to switch to JSON - this implementation wouldn't need much of a change.*/
  val ITEMS: Map[Category, Vector[String]] = {
    Map(
      Legendary -> Vector("Sulfuras, Hand of Ragnaros"),
      Conjured -> Vector("Conjured Mana Cake"),
      Common -> Vector("Elixir of the Mongoose", "+5 Dexterity Vest"),
      BackStagePass -> Vector("Backstage passes to a TAFKAL80ETC concert"),
      Aged -> Vector("Aged Brie")
    )
  }

  def classifyItem(item: Item): Either[UnknownItemCategoryError, Category] = {
    if(isCommon(item))        Right(Common) else
    if(isConjured(item))      Right(Conjured) else
    if(isBackStagePass(item)) Right(BackStagePass) else
    if(isLegendary(item))     Right(Legendary) else
    if(isAged(item))          Right(Aged) else Left(UnknownItemCategoryError(item))
  }

  private def isCommon(item: Item): Boolean =         ITEMS(Common).contains(item.name)
  private def isLegendary(item: Item): Boolean =      ITEMS(Legendary).contains(item.name)
  private def isConjured(item: Item): Boolean =       ITEMS(Conjured).contains(item.name)
  private def isBackStagePass(item: Item): Boolean =  ITEMS(BackStagePass).contains(item.name)
  private def isAged(item: Item): Boolean =           ITEMS(Aged).contains(item.name)

  /*Option[NegativeItemFieldError] nesting results in poor ergonomics. Using monad transformers could help with this. Im using Either here*/
  def itemIsValid(item: Item): Either[NegativeItemFieldError, Unit] = {
    if(item.sellIn < 0 && item.quality < 0) Left(NegativeItemFieldError(item, SellIn, Quality)) else
    if(item.sellIn < 0) Left(NegativeItemFieldError(item, SellIn)) else
    if(item.quality < 0) Left(NegativeItemFieldError(item, Quality)) else Right(())
  }
}