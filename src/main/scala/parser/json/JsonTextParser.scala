package cz.cvut.fit.oop.hackernews
package parser.json

import parser.TextParser
import parser.json.custom.OptionPickler

class JsonTextParser extends TextParser{
  override def read[R:OptionPickler.ReadWriter](item: String): R = OptionPickler.read[R](item)

  override def write[R:OptionPickler.ReadWriter](item: R): String = OptionPickler.write[R](item)
}
