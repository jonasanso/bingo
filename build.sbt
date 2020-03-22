import Build._

version in ThisBuild := "0.1.0-SNAPSHOT"

lazy val bingo =
  project
    .in(file("bingo"))
    .settings(stdSettings("bingo"))
    .settings(libraryDependencies ++= bingoDeps)

