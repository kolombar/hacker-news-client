name := "hacker-news"

version := "0.1"

scalaVersion := "2.13.7"

idePackagePrefix := Some("cz.cvut.fit.oop.hackernews")

libraryDependencies += "org.scalatest" % "scalatest_2.13" % "3.0.8" % "test"
libraryDependencies += "org.mockito" % "mockito-scala_2.13" % "1.6.2" % "test"
libraryDependencies += "com.lihaoyi" %% "upickle" % "0.9.5"

assemblyJarName in assembly := "HackerNewsClient.jar"
