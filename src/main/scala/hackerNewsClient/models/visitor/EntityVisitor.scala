package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models.visitor

import hackerNewsClient.models.{Item, User}

trait EntityVisitor[T] {
  def visitStory(entity: Item): T

  def visitComment(entity: Item): T

  def visitUser(entity: User): T
}
