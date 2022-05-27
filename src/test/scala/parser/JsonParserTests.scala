package cz.cvut.fit.oop.hackernews
package parser

import parser.json.JsonTextParser
import parser.json.custom.OptionPickler

import org.scalatest.FunSuite

case class JsonParserTestElem(key: Int, value: String)

object JsonParserTestElem {
  implicit val rw: OptionPickler.ReadWriter[JsonParserTestElem] = OptionPickler.macroRW
}

class JsonParserTests extends FunSuite{
  val parser = new JsonTextParser

  test("Read valid json") {
    val json = "{\"key\":1,\"value\":\"hello\"}"
    val expectedElem = JsonParserTestElem(1, "hello")
    val result = parser.read[JsonParserTestElem](json)

    assert(result == expectedElem)
  }

  test("Missing field") {
    val json = "{\"key\":1}"
    try {
      parser.read[JsonParserTestElem](json)
    } catch {
      case e: upickle.core.AbortException =>
    }
  }

  test("Write json") {
    val expectedJson = "{\"key\":1,\"value\":\"hello\"}"
    val elem = JsonParserTestElem(1, "hello")
    val result = parser.write[JsonParserTestElem](elem)

    assert(result == expectedJson)
  }
}
