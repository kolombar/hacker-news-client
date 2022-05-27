package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models

import repositories.models.Entity

import cz.cvut.fit.oop.hackernews.hackerNewsClient.models.visitor.EntityVisitor

trait ApiResponseEntity[T] extends Entity[T]{
  def accept[R](visitor: EntityVisitor[R]): R
}
