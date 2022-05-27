package cz.cvut.fit.oop.hackernews
package exporter

import java.io.{File, FileOutputStream}

class FileStreamTextExporter(file: File) extends StreamTextExporter(new FileOutputStream(file, true)) {
}
