enablePlugins(spray.boilerplate.BoilerplatePlugin)

name         := "scalatest-csv-table"
scalaVersion := "2.12.8"
version      := "1.0.0"
organization := "com.github.akiomik"

lazy val kantanCsvVersion = "0.5.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5",

  "com.nrinaudo"  %% "kantan.csv"         % kantanCsvVersion,
  "com.nrinaudo"  %% "kantan.csv-generic" % kantanCsvVersion,
)
