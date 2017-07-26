package models

import java.awt.image.BufferedImage

import play.api.data._
import play.api.data.Forms._

import scala.collection.mutable.ArrayBuffer

case class Item(name: String, description: String,
                maker: String, warranty: Int,
                price: Int, discount: Int,
                seller: String)

object Item {

  val createItemForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "maker" -> nonEmptyText,
      "warranty" -> number(min = 0, max = 5),
      "price" -> number(min = 0, max = 100),
      "discount" -> number,
      "seller" -> nonEmptyText
    )(Item.apply)(Item.unapply)
  )

  val items = ArrayBuffer(
    Item("name1", "description1", "maker1", 1, 2, 0, "seller1"),
    Item("name2", "description2", "maker2", 1, 2, 0, "seller2"),
    Item("name3", "description3", "maker3", 1, 2, 0, "seller3")
  )

}