import Build._

version in ThisBuild := "0.1.0-SNAPSHOT"

lazy val bingo =
  project
    .in(file("bingo"))
    .settings(stdSettings("bingo"))
    .settings(libraryDependencies ++= bingoDeps)
    .enablePlugins(GraalVMNativeImagePlugin)
    .settings(graalVMNativeImageOptions ++= Seq(
      "--no-fallback",
      "--initialize-at-build-time",
      "--enable-http",
      "--enable-https",
      "--enable-all-security-services"
    ))


