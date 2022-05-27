package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages.generic

import hackerNewsClient.views.pages.TextPage

class ErrorPage(errorMsg: String) extends TextPage {
  override def render(): String = {
    val edge = "\n" + "-" * 10 + "\n"
    edge + errorMsg + edge
  }
}
