package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models

import hackerNewsClient.models.visitor.EntityVisitor
import parser.json.custom.OptionPickler

case class Item(id: Int,
                by: Option[String] = None, // prepsat na None
                descendants: Option[Int] = None,
                kids: Option[List[Int]] = None,
                score: Option[Int] = None,
                time: Option[Long] = None,
                title: Option[String] = None,
                `type`: Option[String] = None,
                url: Option[String] = None,
                deleted: Option[Boolean] = None,
                text: Option[String] = None,
                dead: Option[Boolean] = None,
                parent: Option[Int] = None) extends ItemEntity {
  override def accept[R](visitor: EntityVisitor[R]): R = {
    if (`type`.getOrElse("").equals("comment")) return visitor.visitComment(this)
    visitor.visitStory(this)
  }
}

object Item {
  implicit val rw: OptionPickler.ReadWriter[Item] = OptionPickler.macroRW
}
