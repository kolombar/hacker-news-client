package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.User
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

class UserPage(user: User, storiespage: StoriesPage) extends TextPage {
  override def render(): String = {
    val visitor = new EntityTextRenderVisitor
    val renderedUser = user.accept(visitor)
    val edge = "\n" + "-" * 10 + "\n"

    edge + "USER-INFO" + edge + renderedUser + edge + "SUBMITTED" + edge + storiespage.render() + "\n"
  }
}
