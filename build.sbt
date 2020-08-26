name := "zio-sqs-streams"

version := "0.1"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "dev.zio" %% "zio-sqs" % "0.3.0",
  "io.circe" %% "circe-core" % "0.12.3",
  "io.circe" %% "circe-generic" % "0.12.3",
  "io.circe" %% "circe-parser" % "0.12.3"
)

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-deprecation",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-language:postfixOps",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-Ywarn-value-discard",
  "-Ypartial-unification"
)
