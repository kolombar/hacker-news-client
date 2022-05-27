package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.ItemEntity
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

class CommentsPage(item: ItemEntity, commentsPage: StoriesPage) extends TextPage {
  override def render(): String = {
    val visitor = new EntityTextRenderVisitor
    val edge = "\n" + "-" * 10 + "\n"
    edge + "ITEM" + edge + item.accept(visitor) + "\n" + edge + "COMMENTS" + edge + commentsPage.render()
  }
}
