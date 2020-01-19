package com.gildedrose.ItemUtils

/*Since we can not modify our Item class this is needed for a better readability and semantics*/
object ItemFieldsManager {
  /*Even checking if given values are non negative could be a possible refactor in the future*/

  def decrease(value: Int, by: Int) = {
    if (value > by)
      value - by
    else /*Quality or sellIn will never be negative*/
      0
  }

  def increase(value: Int, by: Int, threshold: Int) = {
    val newQuality = value + by
    if (newQuality <= threshold) /*Quality or sellIn will never be over a specific threshold*/
      newQuality
    else
      threshold
  }
}
