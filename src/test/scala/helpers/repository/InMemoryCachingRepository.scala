package cz.cvut.fit.oop.hackernews
package helpers.repository

import downloader.models.DownloaderCacheEntity
import repositories.Repository

class InMemoryCachingRepository extends Repository[String, DownloaderCacheEntity] {
  val createTime = System.currentTimeMillis() / 1000
  var storage: Map[String, DownloaderCacheEntity] = Map()

  override def get(): List[DownloaderCacheEntity] = storage.values.toList

  override def get(id: String): Option[DownloaderCacheEntity] = storage.get(id)

  override def save(entity: DownloaderCacheEntity): Unit = storage = storage + (entity.id -> entity)

  override def clear(): Unit = storage = Map()

  override def remove(id: String): Unit = storage = storage.removed(id)
}
