package com.gildedrose.Category

import com.gildedrose.GildedRoseErrors.GildedRoseError.{NegativeItemFieldError, Quality, SellIn, UnknownItemCategoryError}
import com.gildedrose.Inventory.Inventory.ITEMS
import com.gildedrose.Items.Item
import com.gildedrose.ItemUtils.ItemFieldsManager._

/*Handles each Category*/
object ItemCategoryManager {
  sealed trait Category {
    def updateQuality(sellIn: Int, quality: Int): Int
  }
  case object Legendary extends Category { //Do nothing but maybe later this could change so overriding is necessary
    override def updateQuality(sellIn: Int, quality: Int): Int =
      quality
  }
  case object Aged extends Category {
    override def updateQuality(sellIn: Int, quality: Int): Int =
      increase(quality, 1, 50)
  }
  case object Conjured extends Category {
    override def updateQuality(sellIn: Int, quality: Int): Int = {
      val by = if (sellIn <= 0) 4 else 2
      decrease(quality, by)
    }
  }
  case object Common extends Category {
    override def updateQuality(sellIn: Int, quality: Int): Int = {
      val by = if(sellIn <= 0) 2 else 1 //Once sell time has passed it degrades twice as fast
      decrease(quality, by)
    }
  }
  case object BackStagePass extends Category {
    override def updateQuality(sellIn: Int, quality: Int): Int = {
      val by =
        if (sellIn > 10)                Some(1) else
        if (sellIn <= 10 && sellIn > 5) Some(2) else
        if (sellIn <= 5 && sellIn >= 1) Some(3) else None

      by.fold(0)(by => increase(quality, by, 50))
    }
  }

  def classifyItem(item: Item): Either[UnknownItemCategoryError, Category] = {
    if(isCommon(item))        Right(Common) else
    if(isConjured(item))      Right(Conjured) else
    if(isBackStagePass(item)) Right(BackStagePass) else
    if(isLegendary(item))     Right(Legendary) else
    if(isAged(item))          Right(Aged) else Left(UnknownItemCategoryError(item))
  }

  private def isCommon(item: Item): Boolean =         ITEMS(Common).contains(item.name.trim)
  private def isLegendary(item: Item): Boolean =      ITEMS(Legendary).contains(item.name.trim)
  private def isConjured(item: Item): Boolean =       ITEMS(Conjured).contains(item.name.trim)
  private def isBackStagePass(item: Item): Boolean =  ITEMS(BackStagePass).contains(item.name.trim)
  private def isAged(item: Item): Boolean =           ITEMS(Aged).contains(item.name.trim)

  /*Option[NegativeItemFieldError] nesting results in poor ergonomics. Using monad transformers could help with this. Im using Either here*/
  def itemFieldsAreValid(item: Item): Either[NegativeItemFieldError, Unit] = {
    if(item.sellIn < 0 && item.quality < 0) Left(NegativeItemFieldError(item, SellIn, Quality)) else
    if(item.sellIn < 0) Left(NegativeItemFieldError(item, SellIn)) else
    if(item.quality < 0) Left(NegativeItemFieldError(item, Quality)) else Right(())
  }
}
