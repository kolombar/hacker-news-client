package cz.cvut.fit.oop.hackernews
package hackerNewsClient.downloader

import downloader.models.DownloaderCacheEntity
import downloader.{CachingHttpDownloader, Downloader}
import hackerNewsClient.downloader.models.Updates
import hackerNewsClient.facades.HackerNewsFacade.{ApiType, getUrl}
import parser.json.JsonTextParser
import repositories.Repository

class HackerNewsCachingHttpDownloader(downloader: Downloader, cache: Repository[String, DownloaderCacheEntity], ttl: Option[Int] = None, clearCache: Boolean = false)
  extends CachingHttpDownloader(downloader, cache, ttl, clearCache) {

  override def download(url: String): String = {
    // download updates
    val updates = downloader.download(getUrl(ApiType.UPDATES, None))
    val parser = new JsonTextParser
    val parsedUpdates = parser.read[Updates](updates)
    val updatedItems = parsedUpdates.items
    val updatedUsers = parsedUpdates.profiles

    val updatedItemsKeys = updatedItems.map(item => getUrl(ApiType.ITEM, Some(item)))
    val updatesUsersKeys = updatedUsers.map(user => getUrl(ApiType.USER, Some(user)))

    // if URL is in updates, remove it from cache
    if (updatedItemsKeys.contains(url) || updatesUsersKeys.contains(url)) {
      cache.remove(url)
    }

    super.download(url)
  }
}
