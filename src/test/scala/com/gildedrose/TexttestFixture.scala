package com.gildedrose

import com.gildedrose.Items.Item

object TexttestFixture {
  private val initialItems: Vector[Item] = Vector[Item](
    Item("+5 Dexterity Vest", 10, 20),
    Item("Aged Brie", 2, 0),
    Item("Elixir of the Mongoose", 5, 7),
    Item("Sulfuras, Hand of Ragnaros", 0, 80),
    Item("Sulfuras, Hand of Ragnaros", 1, 80),
    Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
    Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
    Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
    Item("Conjured Mana Cake", 3, 6)
  )

  def main(args: Array[String]): Unit = {
    val app = new GildedRose(initialItems)
    val days = if (args.length > 0) args(0).toInt else 2
    printOutcome(app, days)
  }

  /*Using some sort of library might be great (maybe SLF4J for quick and easy 'lazy logging')*/
  def printOutcome(app: GildedRose, days: Int): Unit = {
    val outcome = app.update(days)
    outcome.fold(println, items => {
      println("name, sellIn, quality")
      items.foreach(i => println(s"${i.name}, ${i.sellIn}, ${i.quality}"))
    })
  }
}
