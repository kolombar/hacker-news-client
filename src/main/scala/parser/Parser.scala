package cz.cvut.fit.oop.hackernews
package parser

import parser.json.custom.OptionPickler

trait Parser[T] {
  def read[R:OptionPickler.ReadWriter](item: T): R

  def write[R:OptionPickler.ReadWriter](item: R): T
}
