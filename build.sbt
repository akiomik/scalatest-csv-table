enablePlugins(spray.boilerplate.BoilerplatePlugin)

lazy val scala211 = "2.11.12"
lazy val scala212 = "2.12.8"

name               := "scalatest-csv-table"
scalaVersion       := scala212
crossScalaVersions := Seq(scala211, scala212)
version            := "1.0.1"
organization       := "com.github.akiomik"
description        := "A scalatest helper for table driven testing with csv"
licenses           += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

lazy val kantanCsvVersion = "0.5.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",

  "com.nrinaudo"  %% "kantan.csv"         % kantanCsvVersion,
  "com.nrinaudo"  %% "kantan.csv-generic" % kantanCsvVersion,
)

bintrayRepository := "maven"
bintrayOrganization := None
