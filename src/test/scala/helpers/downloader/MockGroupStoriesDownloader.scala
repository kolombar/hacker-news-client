package cz.cvut.fit.oop.hackernews
package helpers.downloader

import downloader.Downloader
import hackerNewsClient.models.Item
import helpers.downloader.MockGroupStoriesDownloader.getStory
import helpers.models.TestItem
import parser.json.JsonTextParser

class MockGroupStoriesDownloader extends Downloader {
  override def download(url: String): String = {
    val parser = new JsonTextParser
    val story: Item = getStory()
    if (url.contains("topstories") || url.contains("newstories") || url.contains("beststories")) {
      val top = List(1)
      return parser.write[List[Int]](top)
    }
    parser.write[Item](story)
  }
}

object MockGroupStoriesDownloader {
  def getStory(): Item = new TestItem().withId(1)
}
