name := "akka-typed-examples"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-persistence-typed" % "2.5.23",
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8"
)
