package cz.cvut.fit.oop.hackernews
package hackerNewsClient

import exporter.StdOutStreamTextExporter
import hackerNewsClient.controllers.ConsoleController
import hackerNewsClient.facades.HackerNewsFacade
import hackerNewsClient.views.ConsoleView

import scala.util.Using

object Main extends App {
  Using(new StdOutStreamTextExporter) {exporter => {
    val facade = new HackerNewsFacade()
    val controller = new ConsoleController(facade, exporter)
    val consoleView = new ConsoleView(controller, args)
    consoleView.run()
  }}
}
