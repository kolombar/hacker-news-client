package cz.cvut.fit.oop.hackernews
package exporter

import java.io.OutputStream

/**
 * Exports text to the given stream
 * @param outputStream stream to export to
 */
class StreamTextExporter(outputStream: OutputStream) extends TextExporter with AutoCloseable {
  private var closed = false

  private def exportToStream(text: String): Unit = {
    outputStream.write(text.getBytes("UTF8"))
    outputStream.flush()
  }

  override def close(): Unit = {
    if (closed)
      return

    outputStream.close()
    closed = true
  }

  override def export(item: String): Unit = exportToStream(item)
}
