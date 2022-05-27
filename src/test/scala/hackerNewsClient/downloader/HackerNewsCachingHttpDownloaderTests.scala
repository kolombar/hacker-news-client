package cz.cvut.fit.oop.hackernews
package hackerNewsClient.downloader

import cz.cvut.fit.oop.hackernews.downloader.models.DownloaderCacheEntity
import cz.cvut.fit.oop.hackernews.hackerNewsClient.facades.HackerNewsFacade.ApiType.USER
import cz.cvut.fit.oop.hackernews.hackerNewsClient.facades.HackerNewsFacade.getUrl
import cz.cvut.fit.oop.hackernews.helpers.downloader.MockUpdatesDownloader
import cz.cvut.fit.oop.hackernews.helpers.repository.InMemoryCachingRepository
import org.scalatest.FunSuite

class HackerNewsCachingHttpDownloaderTests extends FunSuite {

  test("check updates with a match") {
    val updatesDownloader = new MockUpdatesDownloader
    val cache = new InMemoryCachingRepository
    val aUrl = getUrl(USER, Some("a"))
    cache.save(new DownloaderCacheEntity(aUrl, "", 0))
    val downloader = new HackerNewsCachingHttpDownloader(updatesDownloader, cache)
    downloader.download(aUrl)

    assert(cache.storage.size == 1)
    assert(cache.get(aUrl).isDefined)
    assert(cache.get(aUrl).get.data == aUrl)
  }

  test("check updates without match") {
    val updatesDownloader = new MockUpdatesDownloader
    val cache = new InMemoryCachingRepository
    val downloader = new HackerNewsCachingHttpDownloader(updatesDownloader, cache)
    val cUrl = getUrl(USER, Some("c"))
    val dUrl = getUrl(USER, Some("d"))
    cache.save(new DownloaderCacheEntity(cUrl, "", 0))
    downloader.download(dUrl)

    assert(cache.storage.size == 2)
    assert(cache.get(cUrl).isDefined)
    assert(cache.get(dUrl).isDefined)
  }
}
