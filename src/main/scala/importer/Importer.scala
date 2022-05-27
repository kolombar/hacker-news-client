package cz.cvut.fit.oop.hackernews
package importer

trait Importer[T] {
  def `import`: Option[T]
}
