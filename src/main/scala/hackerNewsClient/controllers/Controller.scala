package cz.cvut.fit.oop.hackernews
package hackerNewsClient.controllers

import downloader.Downloader
import hackerNewsClient.facades.HackerNewsFacade.ApiType

trait Controller {
  /**
   * Gets top stories from Hacker News API
   * @param limit number of stories to be fetched
   * @param offset starting offset
   * @param downloader client used to fetch API response
   */
  def getGroupStories(limit: Int, offset: Int, group: ApiType.Value, downloader: Downloader): Unit

  /**
   * Gets info about given user from Hacker News API
   * @param id id of the user
   * @param showSubmitted whether user's submitted items should be displayed
   * @param downloader client used to fetch API response
   */
  def getUser(id: String, showSubmitted: Boolean, downloader: Downloader): Unit

  /**
   * Gets comments to the given item
   * @param id id of the item whose comments are going to be fetched
   * @param limit number of comments to be fetched
   * @param offset starting offset
   * @param downloader client used to fetch API response
   */
  def getComments(id: Int, limit: Int, offset: Int, downloader: Downloader): Unit

  /**
   * Gets help (i.e. available commands and their description)
   */
  def getHelp(): Unit
}
