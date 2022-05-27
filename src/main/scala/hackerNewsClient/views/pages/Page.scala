package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

trait Page[T] {
  def render(): T
}
