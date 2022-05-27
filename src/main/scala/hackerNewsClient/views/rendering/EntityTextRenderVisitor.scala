package cz.cvut.fit.oop.hackernews
package hackerNewsClient.views.rendering

import correctors.text.html.{BoldTagsCorrector, ItalicsTagsCorrector, ParagraphTagsCorrector, RemoveTagsCorrector}
import correctors.text.mixed.MixedCorrector
import hackerNewsClient.models.visitor.EntityVisitor
import hackerNewsClient.models.{Item, ItemEntity, User}

import java.time.{Instant, LocalDateTime}
import java.util.TimeZone

class EntityTextRenderVisitor extends EntityVisitor[String] {
    val corrector = new MixedCorrector(Seq(new ItalicsTagsCorrector, new BoldTagsCorrector, new ParagraphTagsCorrector, new RemoveTagsCorrector))

    override def visitStory(entity: Item): String = {
        // prepare data
        val printableTitle = entity.title.getOrElse("---")
        val printableUrl = entity.url.getOrElse("---")
        val printableScore = entity.score match {
            case Some(score) => score.toString
            case None => "-"
        }
        val printableBy = entity.by.getOrElse("-")
        val printableDescendants = entity.descendants match {
            case Some(descendants) => descendants.toString
            case None => "-"
        }

        // format output
        printableTitle + " (" + printableUrl + ")\n" + printableScore + " points by " + printableBy + " | " + printableDescendants + " comments"
    }

    override def visitComment(entity: Item): String = {
        // prepare data
        val createDate = entity.time match {
            case None => "---"
            case Some(time) => prepareDate(time)
        }
        val text = corrector.correct(prepareText(entity))

        // format output
        "by " + entity.by.getOrElse("---") + " on " + createDate + " | " + text
    }

    override def visitUser(entity: User): String = {
        // prepare data
        val createDate = prepareDate(entity.created)
        val aboutPrintable = corrector.correct(entity.about.getOrElse("---"))
        val submittedPrintable = entity.submitted match {
            case Some(submitted) => prepareSubmitted(submitted)
            case None => "---"
        }

        // format output
        "user: " + entity.id + "\ncreated: " + createDate + "\nkarma: " + entity.karma + "\nabout: " + aboutPrintable + "\nsubmitted: " + submittedPrintable + "\n"
    }

    private def prepareText(entity: ItemEntity): String = {
        entity.deleted match {
            case Some(true) => "<i>deleted</i>"
            case _ => entity.text.getOrElse("<i>no text</i>")
        }
    }

    private def prepareDate(timestamp: Long): String = {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone.getDefault().toZoneId()).toString
    }

    private def prepareSubmitted(submitted: List[Int]): String = {
        submitted.length.toString
    }
}
