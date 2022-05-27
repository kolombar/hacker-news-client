package cz.cvut.fit.oop.hackernews
package correctors

trait Corrector[T] {
  def correct(item: T): T
}
