package cz.cvut.fit.oop.hackernews
package hackerNewsClient.facades

import hackerNewsClient.facades.HackerNewsFacade.ApiType.TOP_STORIES
import helpers.downloader.{MockCommentsDownloader, MockGroupStoriesDownloader, MockUserDownloader}

import org.scalatest.FunSuite

class HackerNewsFacadeTests extends FunSuite {
  val facade = new HackerNewsFacade

  test("top-stories") {
    val topStories = facade.getGroupStories(10, 0, TOP_STORIES, new MockGroupStoriesDownloader)
    val id = MockGroupStoriesDownloader.getStory().id

    assert(topStories.length == 1)
    assert(topStories(0) == id)
  }

  test("user-info") {
    val user = facade.getUser("abc", new MockUserDownloader)

    assert(user == MockUserDownloader.getUser())
  }

  test("get-comments") {
    val comments = facade.getItems(List(1), new MockCommentsDownloader)

    assert(comments.size == 1)
    assert(comments.toList(0) == MockCommentsDownloader.getComment())
  }
}
