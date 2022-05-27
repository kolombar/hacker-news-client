package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models

import parser.json.custom.OptionPickler

import hackerNewsClient.models.visitor.EntityVisitor

case class User(id: String,
                created: Long,
                karma: Int,
                about: Option[String] = Option.empty,
                submitted: Option[List[Int]] = Option.empty) extends UserEntity {
    override def accept[R](visitor: EntityVisitor[R]): R = {
        visitor.visitUser(this)
    }
}

object User {
    implicit val rw: OptionPickler.ReadWriter[User] = OptionPickler.macroRW
}