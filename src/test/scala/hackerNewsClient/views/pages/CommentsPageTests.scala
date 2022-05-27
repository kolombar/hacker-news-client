package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.{Item, ItemEntity}
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

import cz.cvut.fit.oop.hackernews.helpers.models.TestItem
import org.scalatest.FunSuite

class CommentsPageTests extends FunSuite {
  protected def renderFor(item: ItemEntity): String = item.accept(new EntityTextRenderVisitor)


  test("One comment") {
    val story = getItem(1, "story")

    val comment = getItem(2, "comment")
    val storiesPage = new StoriesPage(List(comment))

    val page = new CommentsPage(story, storiesPage)

    assert(page.render() == expectedFormat(renderFor(story), storiesPage.render()))
  }

  test("Two comments") {
    val story = getItem(1, "story")

    val comment1 = getItem(2, "comment")

    val comment2 = getItem(3, "comment")

    val page = new CommentsPage(story, new StoriesPage(List(comment1, comment2)))
    val storiesPage = new StoriesPage(List(comment1, comment2))

    assert(page.render() == expectedFormat(renderFor(story), storiesPage.render()))
  }

  test("No comments") {
    val story = getItem(1, "story")

    val page = new CommentsPage(story, new StoriesPage(List.empty))
    val storiesPage = new StoriesPage(List.empty)

    assert(page.render() == expectedFormat(renderFor(story), storiesPage.render()))
  }

  private def getItem(id: Int, `type`: String): Item = {
    new TestItem()
      .withId(id)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType(`type`)
  }

  private def expectedFormat(item: String, comments: String): String = {
    val edge = "\n" + "-" * 10 + "\n"
    edge + "ITEM" + edge + item + "\n" + edge + "COMMENTS" + edge + comments
  }
}
