package cz.cvut.fit.oop.hackernews
package correctors.text.html

import correctors.text.TextCorrector

class ParagraphTagsCorrector extends TextCorrector {
  override def correct(item: String): String = {
    val paragraphTagRegex = "(<p>)"

    item.replaceAll(paragraphTagRegex, "\n")
  }
}
