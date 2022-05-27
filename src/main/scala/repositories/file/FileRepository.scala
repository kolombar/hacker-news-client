package cz.cvut.fit.oop.hackernews
package repositories.file

import exporter.FileStreamTextExporter
import importer.FileTextImporter
import parser.json.JsonTextParser
import parser.json.custom.OptionPickler
import repositories.Repository
import repositories.models.Entity

import java.io.{File, FileOutputStream}

class FileRepository[ID, T <: Entity[ID]](location: File)(implicit rw: OptionPickler.ReadWriter[T]) extends Repository[ID, T] {
  private val fileExporter = new FileStreamTextExporter(location)
  private val fileImporter = new FileTextImporter(location)

  private val parser = new JsonTextParser

  override def get(): List[T] = {
    val recordsOpt = fileImporter.`import`

    if (recordsOpt.isEmpty) return List.empty

    val records = recordsOpt.get
    records.map(record => parser.read[T](record))
  }

  def get(id: ID): Option[T] = {
    val recordsOpt = fileImporter.`import`

    if (recordsOpt.isEmpty) return None

    val records = recordsOpt.get
    for (record <- records) {
      val entity = parser.read[T](record)
      if (entity.id == id)
        return Some(entity)
    }
    None
  }

  override def save(entity: T): Unit = {
    if (exists(entity))
      remove(entity.id)

    val jsonItem = OptionPickler.write(entity)
    fileExporter.`export`(jsonItem + "\n")
  }

  override def clear(): Unit = {
    if (location.exists) {
      clearFile(location)
    }
  }

  override def remove(id: ID): Unit = {
    val recordsOpt = fileImporter.`import`

    if (recordsOpt.isEmpty) return

    val records = recordsOpt.get
    val entities = records.map(record => parser.read[T](record))

    // remove item with given ID
    val repositoryItemsWithoutRemoved = entities.filterNot(item => item.id == id)
    clear()
    // write back the rest
    repositoryItemsWithoutRemoved.foreach(item => {
      val jsonItem = OptionPickler.write(item)
      fileExporter.`export`(jsonItem + "\n")
    })
  }

  private def clearFile(file: File): Unit = new FileOutputStream(file.getPath).close()

  private def exists(entity: T): Boolean = {
    get(entity.id) match {
      case Some(_) => true
      case None => false
    }
  }
}
