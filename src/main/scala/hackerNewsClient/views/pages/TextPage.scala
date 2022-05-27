package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

trait TextPage extends Page[String] {
  def render(): String
}
