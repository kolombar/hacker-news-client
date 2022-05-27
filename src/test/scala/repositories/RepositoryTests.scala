package cz.cvut.fit.oop.hackernews
package repositories

import parser.json.custom.OptionPickler
import repositories.file.FileRepository
import repositories.models.Entity

import org.scalatest.FunSuite

import java.io.File

abstract class RepositoryTests[ID, T <: Entity[ID]](implicit rw: OptionPickler.ReadWriter[T]) extends FunSuite {
  val testLocation = "src/test/scala/repositories/files/test.repo"
  val repositoryFile = new File(testLocation)

  def getRandomElement(id: ID): T

  def getNextId: ID

  def getNextElement: T = getRandomElement(getNextId)

  test("Get empty"){
    val repository = new FileRepository[ID, T](repositoryFile)
    assert(repository.get().isEmpty)

    repository.clear()
  }

  test("Get some"){
    val repository = new FileRepository[ID, T](repositoryFile)
    val element1 = getNextElement
    val element2 = getNextElement

    repository.save(element1)
    repository.save(element2)

    assert(repository.get().length == 2)

    val getResult = repository.get()
    assert(getResult.head == element1 || getResult.head == element2)
    assert(getResult(1) == element1 || getResult(1) == element2)

    repository.clear()
  }

  test("Get id + save new"){
    val repository = new FileRepository[ID, T](repositoryFile)
    val element = getNextElement
    repository.save(element)

    assert(repository.get(element.id).get.id == element.id)
    assert(repository.get(element.id).get == element)

    repository.clear()
  }

  test("Get not found"){
    val repository = new FileRepository[ID, T](repositoryFile)
    assert(repository.get(getNextElement.id).isEmpty)

    repository.clear()
  }

  test("Save existing"){
    val repository = new FileRepository[ID, T](repositoryFile)
    val element = getNextElement
    repository.save(element)

    assert(repository.get(element.id).get.id == element.id)
    assert(repository.get(element.id).get == element)

    val newElementVal = getRandomElement(element.id)
    repository.save(newElementVal)

    assert(repository.get(element.id).get.id == element.id)
    assert(repository.get(element.id).get.id == newElementVal.id)
    assert(repository.get(element.id).get == newElementVal)

    repository.clear()
  }
}
