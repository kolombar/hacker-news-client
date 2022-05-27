package cz.cvut.fit.oop.hackernews
package repositories.files

import parser.json.custom.OptionPickler
import repositories.RepositoryTests
import repositories.models.Entity

case class FileRepositoryTestElem(id: Int, data: String) extends Entity[Int]

object FileRepositoryTestElem {
  implicit val rw: OptionPickler.ReadWriter[FileRepositoryTestElem] = OptionPickler.macroRW
}

class FileRepositoryTests extends RepositoryTests[Int, FileRepositoryTestElem] {
  var currentId = 0

  override def getRandomElement(id: Int): FileRepositoryTestElem = FileRepositoryTestElem(id, id.toString)

  override def getNextId: Int = {
    currentId += 1
    currentId - 1
  }
}
