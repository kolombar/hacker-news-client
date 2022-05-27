package cz.cvut.fit.oop.hackernews
package correctors.text.html

import correctors.text.TextCorrector

class RemoveTagsCorrector extends TextCorrector {
  override def correct(item: String): String = {
    val allTagRegex = "</?[^<>]+>"

    item.replaceAll(allTagRegex, "")
  }
}
