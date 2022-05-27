package cz.cvut.fit.oop.hackernews
package downloader

trait Downloader {
    def download(url: String): String
}
