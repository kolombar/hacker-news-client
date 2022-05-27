package cz.cvut.fit.oop.hackernews
package exporter

trait Exporter[T] {
    def export(item: T): Unit
}
