package cz.cvut.fit.oop.hackernews
package helpers.models

import hackerNewsClient.models.Item

class TestItem(override val id: Int = 0,
                    override val by: Option[String] = None,
                    override val descendants: Option[Int] = None,
                    override val kids: Option[List[Int]] = None,
                    override val score: Option[Int] = None,
                    override val time: Option[Long] = None,
                    override val title: Option[String] = None,
                    override val `type`: Option[String] = None,
                    override val url: Option[String] = None,
                    override val deleted: Option[Boolean] = None,
                    override val text: Option[String] = None,
                    override val dead: Option[Boolean] = None,
                    override val parent: Option[Int] = None) extends Item(0, by, descendants, kids, score, time, title, `type`, url, deleted, text, dead, parent) {
  def withId(id: Int): TestItem =
    new TestItem(id, this.by, this.descendants, this.kids, this.score, this.time, this.title, this.`type`, this.url, this.deleted, this.text, this.dead, this.parent)

  def withTitle(title: String): TestItem =
    new TestItem(this.id, this.by, this.descendants, this.kids, this.score, this.time, Some(title), this.`type`, this.url, this.deleted, this.text, this.dead, this.parent)

  def withType(`type`: String): TestItem =
    new TestItem(this.id, this.by, this.descendants, this.kids, this.score, this.time, this.title, Some(`type`), this.url, this.deleted, this.text, this.dead, this.parent)

  def withBy(by: String): TestItem =
    new TestItem(this.id, Some(by), this.descendants, this.kids, this.score, this.time, this.title, this.`type`, this.url, this.deleted, this.text, this.dead, this.parent)

  def withTime(time: Long): TestItem =
    new TestItem(this.id, this.by, this.descendants, this.kids, this.score, Some(time), this.title, this.`type`, this.url, this.deleted, this.text, this.dead, this.parent)

  def withText(text: String): TestItem =
    new TestItem(this.id, this.by, this.descendants, this.kids, this.score, this.time, this.title, this.`type`, this.url, this.deleted, Some(text), this.dead, this.parent)
}
