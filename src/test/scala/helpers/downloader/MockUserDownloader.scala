package cz.cvut.fit.oop.hackernews
package helpers.downloader

import downloader.Downloader
import hackerNewsClient.models.User
import helpers.downloader.MockUserDownloader.getUser
import parser.json.JsonTextParser

class MockUserDownloader extends Downloader {
  override def download(url: String): String = {
    val parser = new JsonTextParser
    val user = getUser()
    parser.write[User](user)
  }
}

object MockUserDownloader {
  def getUser(): User = User("abc", 100, 10, Some("text"), None)
}
