package cz.cvut.fit.oop.hackernews
package repositories.models

import hackerNewsClient.models.visitor.EntityVisitor

/**
 * Entity with unique identificator
 * @tparam T type of identificator
 */
trait Entity[T] {
  def id: T
}