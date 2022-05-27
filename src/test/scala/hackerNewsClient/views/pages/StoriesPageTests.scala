package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.ItemEntity
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

import cz.cvut.fit.oop.hackernews.helpers.models.TestItem
import org.scalatest.FunSuite

class StoriesPageTests extends FunSuite {
  protected def renderFor(item: ItemEntity): String = item.accept(new EntityTextRenderVisitor)


  test("One story") {
    val story = new TestItem()
      .withId(1)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")

    val page = new StoriesPage(List(story))

    assert(page.render() == expectedFormat(List(renderFor(story))))
  }

  test("Two stories") {
    val story1 = new TestItem()
      .withId(1)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")

    val story2 = new TestItem()
      .withId(2)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")

    val page = new StoriesPage(List(story1, story2))

    assert(page.render() == expectedFormat(List(renderFor(story1), renderFor(story2))))
  }

  test("Empty page") {
    val page = new StoriesPage(List.empty)

    assert(page.render() == expectedFormat(List.empty))
  }

  private def expectedFormat(stories: List[String]): String = {
    val edge = "\n" + "-" * 5 + "\n"
    edge + stories.mkString(edge) + edge
  }
}
