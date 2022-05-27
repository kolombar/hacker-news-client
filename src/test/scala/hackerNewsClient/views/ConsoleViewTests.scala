package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views

import downloader.Downloader
import hackerNewsClient.controllers.Controller
import hackerNewsClient.facades.HackerNewsFacade.ApiType
import hackerNewsClient.facades.HackerNewsFacade.ApiType.{BEST_STORIES, NEW_STORIES, TOP_STORIES}

import org.mockito.MockitoSugar.{mock, verify, verifyZeroInteractions}
import org.mockito.captor.ArgCaptor
import org.scalatest.FunSuite

class ConsoleViewTests extends FunSuite {
  test("Random command") {
    val mockController = mock[Controller]
    val args: Array[String] = Array.empty
    val view = new ConsoleView(mockController, args)

    //Process a command
    view.processCommand("Some random command", args.toList)

    //Verify no interactions
    verifyZeroInteractions(mockController)
  }

  test("Top-stories command") {
    testGroupStoriesCommandsWithoutArgs("top-stories", TOP_STORIES)
  }

  test("Best-stories command") {
    testGroupStoriesCommandsWithoutArgs("best-stories", BEST_STORIES)
  }

  test("New-stories command") {
    testGroupStoriesCommandsWithoutArgs("new-stories", NEW_STORIES)
  }

  test("Top-stories command with args") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array("--limit=20", "--offset=5")
    val view = new ConsoleView(mockController, args)

    val limitCapture = ArgCaptor[Int]
    val offsetCapture = ArgCaptor[Int]
    val groupCapture = ArgCaptor[ApiType.Value]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("top-stories", args.toList)

    //Verify that the method was called once
    verify(mockController).getGroupStories(limitCapture, offsetCapture, groupCapture, downloaderCapture)

    //Verify parameters
    assert(limitCapture.value == 20)
    assert(offsetCapture.value == 5)
    assert(groupCapture.value == TOP_STORIES)
  }

  test("User-info command") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array("--name=abc")
    val view = new ConsoleView(mockController, args)

    val idCapture = ArgCaptor[String]
    val showCapture = ArgCaptor[Boolean]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("user-info", args.toList)

    //Verify that the method was called once
    verify(mockController).getUser(idCapture, showCapture, downloaderCapture)

    //Verify parameters
    assert(idCapture.value == "abc")
    assert(!showCapture.value)
  }

  test("User-info command with missing id") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array.empty
    val view = new ConsoleView(mockController, args)

    val idCapture = ArgCaptor[String]
    val showCapture = ArgCaptor[Boolean]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("user-info", args.toList)

    verify(mockController).getUser(idCapture, showCapture, downloaderCapture)

    assert(idCapture.value.isEmpty)
  }

  test("Get-comments command") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array("--item=123", "--limit=50", "-offset=2")
    val view = new ConsoleView(mockController, args)

    val idCapture = ArgCaptor[Int]
    val limitCapture = ArgCaptor[Int]
    val offsetCapture = ArgCaptor[Int]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("get-comments", args.toList)

    //Verify that the method was called once
    verify(mockController).getComments(idCapture, limitCapture, offsetCapture, downloaderCapture)

    //Verify parameters
    assert(idCapture.value == 123)
    assert(limitCapture.value == 50)
    assert(offsetCapture.value == 0)
  }

  test("Get-comments command with missing id") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array.empty
    val view = new ConsoleView(mockController, args)

    val idCapture = ArgCaptor[Int]
    val limitCapture = ArgCaptor[Int]
    val offsetCapture = ArgCaptor[Int]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("get-comments", args.toList)

    verify(mockController).getComments(idCapture, limitCapture, offsetCapture, downloaderCapture)

    assert(idCapture.value == -1)
  }

  test("Top stories with invalid args") {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array("--limit=abc", "--offset=1b2")
    val view = new ConsoleView(mockController, args)

    val limitCapture = ArgCaptor[Int]
    val offsetCapture = ArgCaptor[Int]
    val groupCapture = ArgCaptor[ApiType.Value]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand("top-stories", args.toList)

    //Verify that the method was called once
    verify(mockController).getGroupStories(limitCapture, offsetCapture, groupCapture, downloaderCapture)

    assert(limitCapture.value == 10)
    assert(offsetCapture.value == 0)
    assert(groupCapture.value == TOP_STORIES)
  }

  private def testGroupStoriesCommandsWithoutArgs(command: String, group: ApiType.Value): Unit = {
    //Create view with a mock controller
    val mockController = mock[Controller]
    val args: Array[String] = Array.empty
    val view = new ConsoleView(mockController, args)

    val limitCapture = ArgCaptor[Int]
    val offsetCapture = ArgCaptor[Int]
    val groupCapture = ArgCaptor[ApiType.Value]
    val downloaderCapture = ArgCaptor[Downloader]

    //Process a command
    view.processCommand(command, args.toList)

    //Verify that the method was called once
    verify(mockController).getGroupStories(limitCapture, offsetCapture, groupCapture, downloaderCapture)

    //Verify parameters
    assert(limitCapture.value == 10)
    assert(offsetCapture.value == 0)
    assert(groupCapture.value == group)
  }
}
