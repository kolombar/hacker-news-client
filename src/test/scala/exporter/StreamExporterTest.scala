package cz.cvut.fit.oop.hackernews
package exporter

import org.scalatest.FunSuite

import java.io.ByteArrayOutputStream

class StreamExporterTest extends FunSuite {

  test ("Write") {
    val testSTring = "testing write"
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.`export`(testSTring)
    assert(stream.toString("UTF-8") == testSTring)
  }
}
