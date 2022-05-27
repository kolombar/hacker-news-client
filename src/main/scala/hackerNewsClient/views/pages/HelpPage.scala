package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.pages

import correctors.text.html.BoldTagsCorrector

class HelpPage extends TextPage {
  override def render(): String = {
    val edge = "\n" + "-" * 10 + "\n"
    val help = """<b>top-stories [--limit=int --offset=int]</b> fetches top stories using paging arguments <b>limit</b> (default is 10) and <b>offset</b> (default is 0) to determine which and how many top stories to fetch
                 |
                 |<b>best-stories [--limit=int --offset=int]</b> fetches best stories using paging arguments <b>limit</b> (default is 10) and <b>offset</b> (default is 0) to determine which and how many top stories to fetch
                 |
                 |<b>new-stories [--limit=int. --offset=int]</b> fetches new stories using paging arguments <b>limit</b> (default is 10) and <b>offset</b> (default is 0) to determine which and how many top stories to fetch
                 |
                 |<b>user-info --name=string [--show-submitted=bool]</b> fetches information about user specified by the <b>name</b> argument (providing this argument is mandatory), additionaly items submitted by the user will be displayed if the argument <b>show-submitted</b> is set to <b>true</b>
                 |
                 |<b>get-comments --item=int [--limit=int --offset=int]</b> fetches comments of an item specified by the <b>item</b> argument (mandatory, should by a numeric ID), paging parameters <b>limit</b> and <b>offset</b> work as described above
                 |
                 |There are also two application-wide argument that can be provided with any command:
                 |
                 |<b>--ttl=int</b> specifies time-to-live (in seconds) - all cached items that were added more than <b>ttl</b>-seconds ago will be removed from cache
                 |
                 |<b>--clear-cache</b> clears the cache for storing API responses""".stripMargin
    val boldCorrector = new BoldTagsCorrector
    edge + "HELP" + edge + boldCorrector.correct(help) + "\n"
  }
}
