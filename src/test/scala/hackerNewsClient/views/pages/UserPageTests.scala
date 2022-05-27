package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import hackerNewsClient.models.{Item, User, UserEntity}
import hackerNewsClient.views.rendering.EntityTextRenderVisitor

import org.scalatest.FunSuite

class UserPageTests extends FunSuite {
  protected def renderFor(item: UserEntity): String = item.accept(new EntityTextRenderVisitor)

  test("Render user without submitted") {
    val user = User("abc", 123, 10, Some("bla"), Some(List.empty))

    val submittedPage = new StoriesPage(List.empty)
    val page = new UserPage(user, submittedPage)

    assert(page.render() == expectedFormat(renderFor(user), submittedPage.render()))
  }

  test("Render user with submitted") {
    val submitted = List(1, 2)
    val user = User("abc", 123, 10, Some("bla"), Some(submitted))

    val submittedItem1 = Item(1)
    val submittedItem2 = Item(2)

    val submittedPage = new StoriesPage(List(submittedItem1, submittedItem2))
    val page = new UserPage(user, submittedPage)

    assert(page.render() == expectedFormat(renderFor(user), submittedPage.render()))
  }

  private def expectedFormat(user: String, submitted: String): String = {
    val edge = "\n" + "-" * 10 + "\n"
    edge + "USER-INFO" + edge + user + edge + "SUBMITTED" + edge + submitted + "\n"
  }
}
