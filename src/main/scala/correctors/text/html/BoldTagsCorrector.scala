package cz.cvut.fit.oop.hackernews
package correctors.text.html

import correctors.text.TextCorrector

class BoldTagsCorrector extends TextCorrector {
  override def correct(item: String): String = {
    val boldTagRegex = "<b>([^<>]*)</b>"
    val strongTagRegex = "<strong>([^<>]*)</strong>"

    item.replaceAll(boldTagRegex, makeBold("$1"))
      .replaceAll(strongTagRegex, makeBold("$1"))
  }

  private def makeBold(str: String): String = {
    val ansiBoldStart = "\u001b[1m"
    val ansiBoldEnd = "\u001b[0m"
    ansiBoldStart + str + ansiBoldEnd
  }
}
