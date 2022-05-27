package cz.cvut.fit.oop.hackernews
package helpers.downloader

import downloader.Downloader

class MockIdentityDownloader extends Downloader {
  override def download(url: String): String = url
}
