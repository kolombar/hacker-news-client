package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.facades.HackerNewsFacade.ApiType.{BEST_STORIES, NEW_STORIES, TOP_STORIES}
import hackerNewsClient.models.ItemEntity
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

import cz.cvut.fit.oop.hackernews.helpers.models.TestItem
import org.scalatest.FunSuite

class GroupStoriesPageTests extends FunSuite {
  protected def renderFor(item: ItemEntity): String = item.accept(new EntityTextRenderVisitor)

  test("top-stories") {
    val edge = "\n" + "-" * 5 + "\n"
    val story = new TestItem()
      .withId(1)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")

    val page = new GroupStoriesPage(List(story), TOP_STORIES)

    assert(page.render() == "\nTOP-STORIES" + edge + renderFor(story) + edge)
  }

  test("best-stories") {
    val edge = "\n" + "-" * 5 + "\n"
    val story = new TestItem()
      .withId(1)
      .withBy("MeAgain")
      .withText("Some best text")
      .withTitle("Some Best Title")
      .withType("story")

    val page = new GroupStoriesPage(List(story), BEST_STORIES)

    assert(page.render() == "\nBEST-STORIES" + edge + renderFor(story) + edge)
  }

  test("new-stories") {
    val edge = "\n" + "-" * 5 + "\n"
    val story1 = new TestItem()
      .withId(1)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")
    val story2 = new TestItem()
      .withId(2)
      .withBy("MeAgain")
      .withText("Some best text")
      .withTitle("Some Best Title")
      .withType("story")

    val storiesList = List(story1, story2)
    val page = new GroupStoriesPage(storiesList, NEW_STORIES)

    assert(page.render() == "\nNEW-STORIES" + edge + storiesList.map(story => renderFor(story)).mkString(edge) + edge)
  }
}
