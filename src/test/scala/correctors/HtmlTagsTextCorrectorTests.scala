package cz.cvut.fit.oop.hackernews
package correctors

import correctors.text.html.{BoldTagsCorrector, ItalicsTagsCorrector, ParagraphTagsCorrector, RemoveTagsCorrector}

import cz.cvut.fit.oop.hackernews.correctors.text.TextCorrector
import cz.cvut.fit.oop.hackernews.correctors.text.mixed.MixedCorrector
import org.scalatest.FunSuite

class HtmlTagsTextCorrectorTests extends FunSuite {
  test ("Italics tags") {
    val corrector = new ItalicsTagsCorrector
    val testString = "This is <i>my super</i> test string that will <em>hopefully</em> pass all tests."
    val expectedText = "This is \u001b[3mmy super\u001b[0m test string that will \u001b[3mhopefully\u001b[0m pass all tests."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "This one is <i>not<i> valid."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test ("Wrong italics tags") {
    val corrector = new ItalicsTagsCorrector
    val testString = "This is <i>my super<i> test string that will <em>hopefully</em> pass all tests."
    val expectedText = "This is <i>my super<i> test string that will \u001b[3mhopefully\u001b[0m pass all tests."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "This one is <i>not<i> valid."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test ("Bold tags") {
    val corrector = new BoldTagsCorrector
    val testString = "This is <b>my super</b> test string that will <strong>hopefully</strong> pass all <strong>tests</strong>."
    val expectedText = "This is \u001b[1mmy super\u001b[0m test string that will \u001b[1mhopefully\u001b[0m pass all \u001b[1mtests\u001b[0m."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "No <bvalid</b> tags </strong>here</strong>."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test ("Wrong bold tags") {
    val corrector = new BoldTagsCorrector
    val testString = "This is <b>my super</b> test string that will <strong>hopefully pass all <strong>tests</strong>."
    val expectedText = "This is \u001b[1mmy super\u001b[0m test string that will <strong>hopefully pass all \u001b[1mtests\u001b[0m."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "No <bvalid</b> tags </strong>here</strong>."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test ("Paragraph tags") {
    val corrector = new ParagraphTagsCorrector
    val testString = "This is my super test string<p>that will hopefully pass all tests."
    val expectedText = "This is my super test string\nthat will hopefully pass all tests."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "No valid </p>paragraph here."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test ("Remove tags") {
    val corrector = new RemoveTagsCorrector
    val testString = "Some <a href=www.facebook.com>random tags</a> here."
    val expectedText = "Some random tags here."

    testCorrector(corrector, testString, expectedText)

    val anotherTestString = "Not <a<ba valid tag."
    testCorrector(corrector, anotherTestString, anotherTestString)
  }

  test("All html tags") {
    val corrector = getMixedCorrector()
    val testString = "Try to avoid getting bogged down in 'fairness'. If your <b>startup</b> makes a nice exit, <strong>chances</strong> are that someone will get more <em>money than they 'deserve'</em> (hardly ever you), and someone will get less money than they 'deserve' (almost always you). Alternatively you could spend all your time fighting to make it 'fair', and end up with a fair slice of zero because your startup failed. Keep this in mind when choosing co-founders as well. If your college roomate says he doesn't want to share the electric bill equally because his lights are off more often than yours; what will he be like when there is <i>real money</i> on the table?<p>Go ahead and negotiate equity with the goal of maximizing your absolute monetary return. If fighting over $100k in equity costs you $200k of lost opportunities, you are not winning."
    val expectedText = "Try to avoid getting bogged down in 'fairness'. If your \u001b[1mstartup\u001b[0m makes a nice exit, \u001b[1mchances\u001b[0m are that someone will get more \u001b[3mmoney than they 'deserve'\u001b[0m (hardly ever you), and someone will get less money than they 'deserve' (almost always you). Alternatively you could spend all your time fighting to make it 'fair', and end up with a fair slice of zero because your startup failed. Keep this in mind when choosing co-founders as well. If your college roomate says he doesn't want to share the electric bill equally because his lights are off more often than yours; what will he be like when there is \u001b[3mreal money\u001b[0m on the table?\nGo ahead and negotiate equity with the goal of maximizing your absolute monetary return. If fighting over $100k in equity costs you $200k of lost opportunities, you are not winning."

    testCorrector(corrector, testString, expectedText)
  }

  test("No tags") {
    val corrector = getMixedCorrector()
    val testString = "I don't have any tags."
    val correctedText = corrector.correct(testString)
    val expectedText = testString

    assert(correctedText == expectedText)
  }

  private def testCorrector(corrector: TextCorrector, inputText: String, expectedText: String): Unit = {
    val correctedText = corrector.correct(inputText)

    assert(correctedText == expectedText)
  }

  private def getMixedCorrector(): MixedCorrector = {
    new MixedCorrector(Seq(new ItalicsTagsCorrector, new BoldTagsCorrector, new ParagraphTagsCorrector, new RemoveTagsCorrector))
  }

}
