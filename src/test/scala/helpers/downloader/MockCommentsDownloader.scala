package cz.cvut.fit.oop.hackernews
package helpers.downloader

import downloader.Downloader
import hackerNewsClient.models.Item
import helpers.downloader.MockCommentsDownloader.getComment
import helpers.models.TestItem
import parser.json.JsonTextParser

class MockCommentsDownloader extends Downloader {
  override def download(url: String): String = {
    val parser = new JsonTextParser
    val comment = getComment()
    parser.write[Item](comment)
  }
}

object MockCommentsDownloader {
  def getComment(): Item = new TestItem().withId(1).withType("comment")
}
