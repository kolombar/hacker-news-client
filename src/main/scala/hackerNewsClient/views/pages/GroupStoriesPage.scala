package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.facades.HackerNewsFacade.ApiType
import hackerNewsClient.models.ItemEntity

class GroupStoriesPage(stories: Iterable[ItemEntity], group: ApiType.Value) extends StoriesPage(stories) {
  override def render(): String = {
    "\n" + group.toString.toUpperCase + super.render()
  }
}
