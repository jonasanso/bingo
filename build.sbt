import Build._

version in ThisBuild := "0.1.0-SNAPSHOT"

lazy val bingo =
  project
    .in(file("bingo"))
    .settings(stdSettings("bingo"))
    .settings(libraryDependencies ++= bingoDeps)
    .enablePlugins(GraalVMNativeImagePlugin)
    .settings(graalVMNativeImageOptions ++= Seq(
      "--verbose",
      "--no-server",
      "--no-fallback",
      "--enable-http",
      "--enable-https",
      "--enable-all-security-services",
      //"--static", //requires static libc and zlib
      "--report-unsupported-elements-at-runtime",
      "-H:+ReportExceptionStackTraces",
      "-H:+ReportUnsupportedElementsAtRuntime",
      "-H:+TraceClassInitialization",
      "-H:+PrintClassInitialization",
      "--initialize-at-build-time=scala.runtime.Statics$VM",
      "--initialize-at-run-time=java.lang.Math$RandomNumberGeneratorHolder",
    ))


