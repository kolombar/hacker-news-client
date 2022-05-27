package cz.cvut.fit.oop.hackernews
package downloader

import downloader.models.DownloaderCacheEntity
import repositories.Repository


class CachingHttpDownloader(downloader: Downloader, cache: Repository[String, DownloaderCacheEntity], ttl: Option[Int] = None, clearCache: Boolean = false) extends Downloader {

  checkTtlAndClear()

  /**
   * Checks whether cache should be cleared (either because of TTL or because of specific user command)
   */
  private def checkTtlAndClear(): Unit = {
    if (ttl.isDefined) {
      checkTtl(ttl.get)
    }

    if (clearCache)
      cache.clear()
  }

  override def download(url: String): String = {
    val cachedData = cache.get(url)
    if (cachedData.isEmpty) {
      val downloadedData = downloader.download(url)
      cache.save(DownloaderCacheEntity(url, downloadedData, currentTimestampSeconds))
      downloadedData
    } else {
      cachedData.get.data
    }
  }

  /**
   * If cache is too old, it will be cleared
   * @param ttl time-to-live (in seconds)
   */
  private def checkTtl(ttl: Int): Unit = {
    val items = cache.get()
    val validItems = items.filter(item => isTtlValid(item, ttl))
    cache.clear()
    validItems.foreach(item => cache.save(item))
  }

  private def isTtlValid(item: DownloaderCacheEntity, ttl: Int): Boolean = {
    val currentTime = currentTimestampSeconds
    val createTime = item.createTimestampSeconds
    if (createTime + ttl < currentTime)
      return false
    true
  }

  private def currentTimestampSeconds: Long = System.currentTimeMillis() / 1000
}
