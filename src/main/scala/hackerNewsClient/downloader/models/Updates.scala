package cz.cvut.fit.oop.hackernews
package hackerNewsClient.downloader.models

import parser.json.custom.OptionPickler

case class Updates(items: List[Int], profiles: List[String])

object Updates {
  implicit val rw: OptionPickler.ReadWriter[Updates] = OptionPickler.macroRW
}
