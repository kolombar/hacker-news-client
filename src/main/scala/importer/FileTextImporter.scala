package cz.cvut.fit.oop.hackernews
package importer

import java.io.File
import scala.io.Source
import scala.util.{Failure, Success, Using}

class FileTextImporter(srcFile: File) extends TextImporter {
  override def `import`: Option[List[String]] = {
    if (!srcFile.exists())
      return None

    Using(Source.fromFile(srcFile)) { stream => stream.getLines().toList } match {
      case Success(lines) => Some(lines)
      case Failure(ex) => throw new RuntimeException("Unable to import file", ex)
    }
  }
}
