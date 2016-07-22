import sbt.Keys._
import sbt._

object Build extends Build {
  val ScalaVersion = "2.11.7"

  val main = Project("doc-text-extractor", file("."))
    .settings(organization := "in.prags123",
      version := "1.0.0-SNAPSHOT",
      libraryDependencies ++= appDependencies
    )

  lazy val appDependencies = Seq(
    "org.apache.poi" % "poi" % "3.14",
    "org.apache.poi" % "poi-ooxml" % "3.14",
    "org.apache.poi" % "poi-scratchpad" % "3.14",
    "org.apache.poi" % "ooxml-schemas" % "1.3",
    "org.scalatest" %% "scalatest" % "2.2.0" % "test",
    "org.mockito" % "mockito-all" % "1.9.5" % "test"
  )

  override val settings = super.settings ++ Seq(
    fork in run := false,
    resolvers += Resolver.sonatypeRepo("snapshots"),
    parallelExecution in This := false,
    publishMavenStyle := true,
    crossPaths := true,
    publishArtifact in Test := false,
    publishArtifact in(Compile, packageDoc) := false,
    // publishing the main sources jar
    publishArtifact in(Compile, packageSrc) := true
  )
}
