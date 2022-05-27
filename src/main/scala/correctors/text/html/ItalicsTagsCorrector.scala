package cz.cvut.fit.oop.hackernews
package correctors.text.html

import correctors.text.TextCorrector

class ItalicsTagsCorrector extends TextCorrector {
  override def correct(item: String): String = {
    val italicsTagRegex = "<i>([^<>]*)</i>"
    val emTagRegex = "<em>([^<>]*)</em>"

    item.replaceAll(italicsTagRegex, makeItalics("$1"))
      .replaceAll(emTagRegex, makeItalics("$1"))
  }

  private def makeItalics(str: String): String = {
    val ansiItalicsStart = "\u001b[3m"
    val ansiItalicsEnd = "\u001b[0m"
    ansiItalicsStart + str + ansiItalicsEnd
  }
}
