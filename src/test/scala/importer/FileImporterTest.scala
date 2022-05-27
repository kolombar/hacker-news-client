package cz.cvut.fit.oop.hackernews
package importer

import org.scalatest.FunSuite

import java.io.File

class FileImporterTest extends FunSuite {

  test("File import") {
    val file = new File("src/test/scala/importer/files/import-test")
    val importer = new FileTextImporter(file)
    val linesOpt = importer.`import`

    assert(linesOpt.isDefined)

    val lines = linesOpt.get
    assert(lines.length == 2)
    assert(lines(0) == "test1")
    assert(lines(1) == "test2")
  }

  test("Non-existing file import") {
    val file = new File("random")
    val importer = new FileTextImporter(file)
    val linesOpt = importer.`import`

    assert(linesOpt.isEmpty)
  }
}
