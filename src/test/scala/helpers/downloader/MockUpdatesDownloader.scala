package cz.cvut.fit.oop.hackernews
package helpers.downloader

import downloader.Downloader
import hackerNewsClient.downloader.models.Updates
import parser.json.JsonTextParser

class MockUpdatesDownloader extends Downloader{
  override def download(url: String): String = {
    if (url.contains("updates")) {
      val updates = new Updates(List(1), List("a", "b"))
      val parser = new JsonTextParser
      parser.write(updates)
    } else {
      url
    }

  }
}
