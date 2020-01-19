name := "GildedRose"

version := "1.0"

scalaVersion := "2.12.1"

scalacOptions += "-Ypartial-unification"
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1",
  "org.typelevel"         %% "cats-core"        % "2.0.0-M4"
)
