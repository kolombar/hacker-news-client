package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views

import downloader.models.DownloaderCacheEntity
import downloader.{Downloader, HttpDownloader}
import hackerNewsClient.controllers.Controller
import hackerNewsClient.downloader.HackerNewsCachingHttpDownloader
import hackerNewsClient.facades.HackerNewsFacade.ApiType.{BEST_STORIES, NEW_STORIES, TOP_STORIES}
import repositories.file.FileRepository

import java.io.File
import scala.util.matching.Regex

/**
 * Processes user commands and their arguments
 * @param controller takes appropriate action based on user input
 * @param args input provided by the user
 */
class ConsoleView(controller: Controller, args: Array[String]) {

  /**
   * Initial phase of user-input processing - separate user commands from command arguments and provide them for further processing
   */
  def run(): Unit = {
    val commands = args.filter(arg => !arg.startsWith("--"))
    val commandOptions = args.filter(arg => arg.startsWith("--"))

    if (commands.length > 1) {
      throw new IllegalArgumentException("Invalid number of commands!")
    }

    val command = commands(0)

    processCommand(command, commandOptions.toList)
  }

  /**
   * Process individual commands together with their arguments
   * @param command commands to process
   * @param args corresponding arguments
   */
  def processCommand(command: String, args: List[String]): Unit = {
    val ttlRegex = "--ttl=([0-9]+)".r
    val ttl = stringToOptionInt(extractArgValue(args, ttlRegex, ""))

    val clearCache: Boolean = args.exists(opt => opt.equals("--clear-cache"))

    // TOP STORIES
    if (command.equals("top-stories") || command.equals("best-stories") || command.equals("new-stories")) {
      val offsetRegex = "--offset=([0-9]+)".r
      val limitRegex = "--limit=([0-9]+)".r
      val offset = extractArgValue(args, offsetRegex, "0").toInt
      val limit = extractArgValue(args, limitRegex, "10").toInt
      val group = command match {
        case "top-stories" => TOP_STORIES
        case "best-stories" => BEST_STORIES
        case "new-stories" => NEW_STORIES
      }

      controller.getGroupStories(limit, offset, group, getDownloader(ttl, clearCache))
      return
    }

    // USER INFO
    if (command.equals("user-info")) {
      val nameRegex = "--name=(.*)".r
      val submittedRegex = "--show-submitted=(.*)".r
      val name = extractArgValue(args, nameRegex, "")
      val showSubmitted = extractArgValue(args, submittedRegex, "false").toBoolean

      controller.getUser(name, showSubmitted, getDownloader(ttl, clearCache))
      return
    }

    // COMMENTS
    if (command.equals("get-comments")) {
      val offsetRegex = "--offset=([0-9]+)".r
      val limitRegex = "--limit=([0-9]+)".r
      val itemRegex = "--item=([0-9]*)".r
      val offset = extractArgValue(args, offsetRegex, "0").toInt
      val limit = extractArgValue(args, limitRegex, "10").toInt
      val id = stringToOptionInt(extractArgValue(args, itemRegex, ""))

      id match {
        case None => controller.getComments(-1, limit, offset, getDownloader(ttl, clearCache))
        case Some(idVal) => controller.getComments(idVal, limit, offset, getDownloader(ttl, clearCache))
      }
      return
    }

    if (command.equals("help")) {
      controller.getHelp()
      return
    }
  }

  private def getDownloader(ttl: Option[Int], clearCache: Boolean): Downloader = {
    val httpDownloader = new HttpDownloader
    val cache = new FileRepository[String, DownloaderCacheEntity](new File(".cache"))
    new HackerNewsCachingHttpDownloader(httpDownloader, cache, ttl, clearCache)
  }

  private def extractArgValue(args: List[String], regex: Regex, defaultValue: String): String = {
    val stringValue = args.find(arg => regex.findFirstMatchIn(arg).isDefined)
    stringValue.getOrElse("") match {
      case regex(value) => value
      case _ => defaultValue
    }
  }

  private def stringToOptionInt(str: String): Option[Int] = {
    str match {
      case "" => None
      case value: String => Some(value.toInt)
    }
  }
}