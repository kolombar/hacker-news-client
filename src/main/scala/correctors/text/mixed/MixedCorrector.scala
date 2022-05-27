package cz.cvut.fit.oop.hackernews
package correctors.text.mixed

import correctors.text.TextCorrector

class MixedCorrector(correctors: Seq[TextCorrector]) extends TextCorrector {
  override def correct(item: String): String = {
    correctors.foldLeft(item)((accumulator, corrector) => corrector.correct(accumulator))
  }
}
