package com.gildedrose

import org.scalatest._

class GildedRoseTest  extends FlatSpec with Matchers {
      it should "foo" in {
        var items = Vector[Item](new Item("foo", 0, 0))
        val app = new GildedRose(items)
        app.updateQuality()
        (app.items(0).name) should equal ("fixme")
      }
}