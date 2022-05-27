package cz.cvut.fit.oop.hackernews
package downloader.caching

import downloader.models.DownloaderCacheEntity
import downloader.{CachingHttpDownloader, Downloader}

import cz.cvut.fit.oop.hackernews.helpers.downloader.MockIdentityDownloader
import cz.cvut.fit.oop.hackernews.helpers.repository.InMemoryCachingRepository
import org.scalatest.FunSuite

class DownloaderCachingTests extends FunSuite {
  test ("Store to cache") {
    val cache = new InMemoryCachingRepository
    val downloader = new MockIdentityDownloader
    val cachingHttpClient = new CachingHttpDownloader(downloader, cache)

    val url1 = "test1"
    testStorage(cachingHttpClient, cache, url1, 1)

    val url2 = "test2"
    testStorage(cachingHttpClient, cache, url2, 2)
    testStorage(cachingHttpClient, cache, url2, 2)

    testStorage(cachingHttpClient, cache, url1, 2)
  }

  test ("TTL") {
    val cache = new InMemoryCachingRepository
    val timestamp = currentTimestamp - 10
    cache.save(DownloaderCacheEntity("a", "b", timestamp))
    cache.save(DownloaderCacheEntity("c", "d", currentTimestamp + 10))
    val downloader = new MockIdentityDownloader
    val cachingHttpClient = new CachingHttpDownloader(downloader, cache, Some(5))

    assert(cache.storage.size == 1)
  }

  test("Clear cache") {
    val cache = new InMemoryCachingRepository
    cache.save(DownloaderCacheEntity("a", "b", currentTimestamp))
    val downloader = new MockIdentityDownloader
    val cachingHttpClient = new CachingHttpDownloader(downloader, cache, None, true)

    assert(cache.storage.isEmpty)
  }

  private def testStorage(cachingHttpClient: Downloader, cache: InMemoryCachingRepository, url: String, total: Int): Unit = {
    cachingHttpClient.download(url)
    assert(cache.storage.size == total)
    val secondCacheInput: DownloaderCacheEntity = cache.get(url).getOrElse(emptyCacheEntity())
    assert(secondCacheInput.data == url)
  }

  private def emptyCacheEntity(): DownloaderCacheEntity = DownloaderCacheEntity("", "", currentTimestamp)

  private def currentTimestamp: Long = System.currentTimeMillis() / 1000
}
