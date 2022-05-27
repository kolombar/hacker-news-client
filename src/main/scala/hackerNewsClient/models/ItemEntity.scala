package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models

import repositories.models.Entity

trait ItemEntity extends ApiResponseEntity[Int] {
    def by: Option[String]
    def descendants: Option[Int]
    def kids: Option[List[Int]]
    def score: Option[Int]
    def time: Option[Long]
    def title: Option[String]
    def `type`: Option[String]
    def url: Option[String]
    def deleted: Option[Boolean]
    def text: Option[String]
    def dead: Option[Boolean]
    def parent: Option[Int]
}
