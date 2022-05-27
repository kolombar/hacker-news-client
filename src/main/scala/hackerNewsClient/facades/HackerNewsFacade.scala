package cz.cvut.fit.oop.hackernews
package hackerNewsClient.facades

import downloader.Downloader
import hackerNewsClient.facades.HackerNewsFacade.{ApiType, getUrl}
import hackerNewsClient.models.{Item, User}
import parser.json.JsonTextParser

/**
 * Provides business logic, namely it fetches and parses Hacker News API responses
 */
class HackerNewsFacade() {
  private val parser = new JsonTextParser

  def getGroupStories(limit:Int, offset: Int, group: ApiType.Value, downloader: Downloader): List[Int] = {
    // download top stories
    val topStoriesUrl = getUrl(group, None)
    val topStoriesRaw = downloader.download(topStoriesUrl)
    // parse response
    val topStoriesIds = try {
      parser.read[List[Int]](topStoriesRaw)
    } catch {
      case e: upickle.core.AbortException => throw new IllegalArgumentException("Got invalid response " + topStoriesUrl + "\nresponse: " + topStoriesRaw, e)
    }

    // apply limit+offset
    topStoriesIds.slice(offset, offset + limit)
  }

  def getUser(id: String, downloader: Downloader): User = {
    // download user info
    val userUrl = getUrl(ApiType.USER, Some(id))
    val userRaw = downloader.download(userUrl)
    // parse response
    val user = try {
      parser.read[User](userRaw)
    } catch {
      case e: upickle.core.AbortException => throw new IllegalArgumentException("Got invalid response " + userUrl + "\nresponse: " + userRaw, e)
    }

    user
  }

  def getItems(ids: Iterable[Int], downloader: Downloader): Iterable[Item] = {
    // download items
    val urls = ids.map(id => getUrl(ApiType.ITEM, Some(id)))
    val stories = urls.map(url => {
      val response = downloader.download(url)
      // parse responses
      val item = try {
        parser.read[Item](response)
      } catch {
        case e: upickle.core.AbortException => throw new IllegalArgumentException("Got invalid response while downloading " + url + "\nresponse: " + response, e)
      }
      item
    })

    stories
  }
}

object HackerNewsFacade {
  // API endpoints
  private val hackerNewsApiUrls = Map(ApiType.TOP_STORIES -> "https://hacker-news.firebaseio.com/v0/topstories.json",
    ApiType.NEW_STORIES -> "https://hacker-news.firebaseio.com/v0/newstories.json",
    ApiType.BEST_STORIES -> "https://hacker-news.firebaseio.com/v0/beststories.json",
    ApiType.ITEM -> "https://hacker-news.firebaseio.com/v0/item/",
    ApiType.USER -> "https://hacker-news.firebaseio.com/v0/user/",
    ApiType.UPDATES -> "https://hacker-news.firebaseio.com/v0/updates.json")

  object ApiType extends Enumeration {
    val TOP_STORIES: ApiType.Value = Value("top-stories")
    val NEW_STORIES: ApiType.Value = Value("new-stories")
    val BEST_STORIES: ApiType.Value = Value("best-stories")
    val ITEM: ApiType.Value = Value("item")
    val USER: ApiType.Value = Value("user")
    val UPDATES: ApiType.Value = Value("updates")
  }

  def getUrl[T](itemType: ApiType.Value, id: Option[T]): String = {
    id match {
      case Some(idValue) => hackerNewsApiUrls(itemType) + idValue + ".json"
      case None => hackerNewsApiUrls(itemType)
    }
  }
}