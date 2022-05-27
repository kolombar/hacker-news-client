package cz.cvut.fit.oop.hackernews
package downloader.models

import parser.json.custom.OptionPickler
import repositories.models.Entity

case class DownloaderCacheEntity(id: String, data: String, createTimestampSeconds: Long) extends Entity[String]

object DownloaderCacheEntity {
  implicit val rw: OptionPickler.ReadWriter[DownloaderCacheEntity] = OptionPickler.macroRW
}
