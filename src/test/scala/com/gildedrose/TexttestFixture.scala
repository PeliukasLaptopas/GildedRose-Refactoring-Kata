package com.gildedrose
import cats.instances.either._
import cats.instances.vector._
import cats.syntax.traverse._

object TexttestFixture {
  def main(args: Array[String]): Unit = {
    var items = Vector[Item](
      new Item("+5 Dexterity Vest", 10, 20),
      new Item("Aged Brie", 2, 0),
      new Item("Elixir of the Mongoose", 5, 7),
      new Item("Sulfuras, Hand of Ragnaros", 0, 80),
      new Item("Sulfuras, Hand of Ragnaros", 1, 80),
      new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
      new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
      new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
      // this conjured item does not work properly yet
      new Item("Conjured Mana Cake", 3, 6)
    )
    val app = new GildedRose(items)
    val days = if (args.length > 0) args(0).toInt + 1 else 2
    System.out.println("name, sellIn, quality")
    for (item <- items) {
      System.out.println(item.name + ", " + item.sellIn + ", " + item.quality)
    }

    println(app.play(10))
  }
}
