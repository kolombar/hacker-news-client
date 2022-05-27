package cz.cvut.fit.oop.hackernews
package hackerNewsClient.controllers

import downloader.Downloader
import exporter.TextExporter
import hackerNewsClient.facades.HackerNewsFacade
import hackerNewsClient.facades.HackerNewsFacade.ApiType
import hackerNewsClient.models.Item
import hackerNewsClient.views.pages.generic.ErrorPage
import hackerNewsClient.views.pages.{CommentsPage, GroupStoriesPage, HelpPage, StoriesPage, TextPage, UserPage}

class ConsoleController(facade: HackerNewsFacade, exporter: TextExporter) extends Controller {

  override def getGroupStories(limit: Int = 10, offset: Int = 0, group: ApiType.Value, downloader: Downloader): Unit = {
    // get IDs of top stories
    val topStories = facade.getGroupStories(limit, offset, group, downloader)
    // get the stories
    val stories = facade.getItems(topStories, downloader)
    // render
    val pages = new GroupStoriesPage(stories, group)
    render(pages)
  }

  override def getUser(id: String, showSubmitted: Boolean, downloader: Downloader): Unit = {
    if (id.isEmpty) {
      render(new ErrorPage("Missing user ID!"))
      return
    }
    // get user
    val user = facade.getUser(id, downloader)
    if (user == null) {
      render(new ErrorPage("Invalid user ID!"))
      return
    }
    // get submitted items if required
    val items = showSubmitted match {
      case false => List.empty
      case true => facade.getItems(user.submitted.getOrElse(List.empty), downloader)
    }

    // render
    val userPage = new UserPage(user, new StoriesPage(items))
    render(userPage)
  }

  override def getComments(id: Int, limit: Int, offset: Int, downloader: Downloader): Unit = {
    if (id == -1) {
      render(new ErrorPage("No item ID provided!"))
      return
    }
    // get the item
    val stories: List[Item] = facade.getItems(List(id), downloader).toList
    val story = stories.head
    if (story == null) {
      render(new ErrorPage("Invalid item ID!"))
      return
    }
    // get its comments
    val commentsList = story.kids.getOrElse(List.empty)
    val slicedCommentsList = commentsList.slice(offset, offset + limit)
    val comments = facade.getItems(slicedCommentsList, downloader)

    // render
    val commentsPage = new CommentsPage(story, new StoriesPage(comments))
    render(commentsPage)
  }

  override def getHelp(): Unit = {
    val page = new HelpPage
    render(page)
  }

  protected def render(renderer: TextPage): Unit = {
    val output = renderer.render()
    exporter.export(output)
  }
}
