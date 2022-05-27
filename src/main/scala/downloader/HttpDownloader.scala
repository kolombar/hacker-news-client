package cz.cvut.fit.oop.hackernews
package downloader

import scala.io.Source
import scala.util.{Failure, Success, Using}

class HttpDownloader extends Downloader {
    override def download(url: String): String = {
        Using(Source.fromURL(url)) { source => source.mkString } match {
            case Success(json) => json
            case Failure(ex) => throw new RuntimeException(s"Unable to fetch $url", ex)
        }
    }
}
