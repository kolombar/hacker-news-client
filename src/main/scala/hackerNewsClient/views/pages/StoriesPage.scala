package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.ItemEntity
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

class StoriesPage(stories: Iterable[ItemEntity]) extends TextPage {
  override def render(): String = {
    val storyVisitor = new EntityTextRenderVisitor
    val edge = "\n" + "-" * 5 + "\n"
    edge + stories.map(story => story.accept(storyVisitor)).mkString(edge) + edge
  }
}
