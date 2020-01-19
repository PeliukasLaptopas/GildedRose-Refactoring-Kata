# GildedRose-Refactoring-Kata in Scala

# PROBLEM AND SOLUTION

Not being able to modifiy Item's class really limits us to refactor this code. Also, making it an abstract class would enable a lot of possibilities for better representations of each Item within each category. Fields could have a wrapper for pattern matching and it would be way easier to not even write code but to read it as well because it would have a lot more power and semantic meaning.

That being said,

My main goal was as always make code semantically correct and readable. Scala's trait are a great way to represent our Inventory system even without being able to modify our Item class.

```scala

  trait Category { <...> }
  case object Legendary extends Category { <...> }
  case object Aged extends Category { <...> }
  case object Conjured extends Category { <...> }
  case object Common extends Category { <...> }
  case object BackStagePass extends Category { <...> }
  ```

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

We have a nice way to represent each Category -
Unfortunately, without being able to make our Item class abstract we can not do a lot of what scala provides. Firstly, SellIn and Quality fields should be wrappers so we could pattern match and write more ergonomical code. Because of that I have to write a method that returns a Category that an Item belongs to. Writing if statements like this aren't the prettiest:

```scala
def classifyItem(item: Item): Either[GildedError, Category] = {
    val maybeClassifiedItem =
      if(isCommon(item))        Common.asRight else
      if(isConjured(item))      Conjured.asRight else
      if(isBackStagePass(item)) BackStagePass.asRight else
      if(isLegendary(item))     Legendary.asRight else
      if(isAged(item))          Aged.asRight else
        UnknownItemCategoryError(item).asLeft

    for {
      _ <- itemFieldsAreValid(item)
      classifiedItem <- maybeClassifiedItem
    } yield classifiedItem
```

It gets very readable and easy to change Item behaviour

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


Because we dont have wrappers for Item's fields - writing separate methods to modify them is a must. So for handling Item SellIn and Quality fields I have these methods:

```scala
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
```
