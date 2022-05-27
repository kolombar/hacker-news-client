package cz.cvut.fit.oop.hackernews
package hackerNewsClient.models

import hackerNewsClient.models.visitor.EntityVisitor

import cz.cvut.fit.oop.hackernews.repositories.models.Entity

trait UserEntity extends ApiResponseEntity[String] {
    def created: Long
    def karma: Int
    def about: Option[String]
    def submitted: Option[List[Int]]
}