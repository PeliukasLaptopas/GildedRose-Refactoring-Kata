# GildedRose-Refactoring-Kata in Scala

Not being able to modifiy Item's class really limits our possibilities to refactor this code. Also, making it an abstract class would enable a lot of possibilities for great representations of each Item within each category.

That being said,
I thought representing each Item in a Category by name would be a great solution if we ever would want to switch Inventory managment (maybe switch to JSON) and it is a very straight forward way to add any new Items to the current system (plus its easy to read).
```scala
val ITEMS: Map[Category, Vector[String]] =
    Map(
      Legendary -> Vector("Sulfuras, Hand of Ragnaros"),
      Conjured -> Vector("Conjured Mana Cake"),
      Common -> Vector("Elixir of the Mongoose", "+5 Dexterity Vest"),
      BackStagePass -> Vector("Backstage passes to a TAFKAL80ETC concert"),
      Aged -> Vector("Aged Brie")
    )
```

My main goal was as always make code semantically correct and readable. Scalas trait are a great way to represent our Inventory system even without being able to modify our Item class.

```scala

  trait Category { <...> }
  case object Legendary extends Category { <...> }
  case object Aged extends Category { <...> }
  case object Conjured extends Category { <...> }
  case object Common extends Category { <...> }
  case object BackStagePass extends Category { <...> }
  ```

By doing so, it gets very readable and easy to change when deciding on how to handle each Items behaviour

```scala
val updateItem: Item => Either[GildedError, Item] = { case item @ Item(name, sellIn, quality) =>
    classifyItem(item).right.map {
      case Common =>        Item(name, decrease(sellIn, 1), Common.updateQuality(sellIn, quality))
      case Conjured =>      Item(name, decrease(sellIn, 1), Conjured.updateQuality(sellIn, quality))
      case BackStagePass => Item(name, decrease(sellIn, 1), BackStagePass.updateQuality(sellIn, quality))
      case Aged =>          Item(name, decrease(sellIn, 1), Aged.updateQuality(sellIn, quality))
      case Legendary =>     item
    }
  }
```
