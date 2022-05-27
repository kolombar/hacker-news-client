package cz.cvut.fit.oop.hackernews
package hackerNewsClient.controllers

import exporter.StreamTextExporter
import hackerNewsClient.facades.HackerNewsFacade
import hackerNewsClient.facades.HackerNewsFacade.ApiType
import hackerNewsClient.facades.HackerNewsFacade.ApiType.{BEST_STORIES, NEW_STORIES, TOP_STORIES}
import hackerNewsClient.views.pages.{CommentsPage, GroupStoriesPage, StoriesPage, UserPage}
import helpers.downloader.{MockCommentsDownloader, MockGroupStoriesDownloader, MockUserDownloader}

import org.scalatest.FunSuite

import java.io.{ByteArrayOutputStream, OutputStream}

class ConsoleControllerTests extends FunSuite {
  protected def getFacade: HackerNewsFacade = {
    new HackerNewsFacade
  }

  protected def getConsoleController(stream: OutputStream): ConsoleController ={
    val facade = getFacade
    val exporter = new StreamTextExporter(stream)
    new ConsoleController(facade, exporter)
  }

  test("top-stories") {
    testGroupStories(TOP_STORIES)
  }

  test("best-stories") {
    testGroupStories(BEST_STORIES)
  }

  test("new-stories") {
    testGroupStories(NEW_STORIES)
  }

  test("user-info") {
    val stream = new ByteArrayOutputStream()
    val controller = getConsoleController(stream)
    val downloader = new MockUserDownloader

    controller.getUser("abc", false, downloader)

    val text = stream.toString("UTF-8")

    assert(text == new UserPage(MockUserDownloader.getUser(), new StoriesPage(List.empty)).render())
  }

  test("get-comments") {
    val stream = new ByteArrayOutputStream()
    val controller = getConsoleController(stream)
    val downloader = new MockCommentsDownloader

    controller.getComments(1, 10, 0, downloader)

    val text = stream.toString("UTF-8")

    assert(text == new CommentsPage(MockCommentsDownloader.getComment(), new StoriesPage(List.empty)).render())
  }

  private def testGroupStories(group: ApiType.Value): Unit = {
    val stream = new ByteArrayOutputStream()
    val controller = getConsoleController(stream)
    val downloader = new MockGroupStoriesDownloader

    controller.getGroupStories(10, 0, group, downloader)

    val text = stream.toString("UTF-8")

    assert(text == new GroupStoriesPage(List(MockGroupStoriesDownloader.getStory()), group).render())
  }
}
