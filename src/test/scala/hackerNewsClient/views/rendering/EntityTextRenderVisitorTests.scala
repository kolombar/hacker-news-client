package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.rendering

import hackerNewsClient.models.User

import cz.cvut.fit.oop.hackernews.helpers.models.TestItem
import org.scalatest.FunSuite

import java.time.{Instant, LocalDateTime}
import java.util.TimeZone

class EntityTextRenderVisitorTests extends FunSuite {
  val visitor = new EntityTextRenderVisitor

  test ("Story render") {
    val story = new TestItem()
      .withId(1)
      .withBy("Me")
      .withText("Some text")
      .withTitle("Some Title")
      .withType("story")

    val renderedStory = story.accept(visitor)

    val expectedOutput = "Some Title (---)\n- points by Me | - comments"
    assert(renderedStory == expectedOutput)
  }

  test ("Comment render") {
    val comment = new TestItem()
      .withId(1)
      .withBy("AgainMe")
      .withType("comment")
      .withTitle("Some super comment")
      .withText("bla bla")

    val renderedComment = comment.accept(visitor)

    val expectedOutput = "by AgainMe on --- | bla bla"

    assert(renderedComment == expectedOutput)
  }

  test("User render") {
    val currentTime = System.currentTimeMillis() / 1000
    val user = User("MyUser", currentTime, 10, Some("something"), Some(List(1, 2, 3)))

    val renderedUser = user.accept(visitor)

    val expectedOutput = "user: MyUser\ncreated: " + prepareDate(currentTime) + "\nkarma: 10\nabout: something\nsubmitted: 3\n"

    assert(renderedUser == expectedOutput)
  }

  private def prepareDate(timestamp: Long): String = {
    LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId()).toString
  }
}
