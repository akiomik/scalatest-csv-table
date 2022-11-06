enablePlugins(spray.boilerplate.BoilerplatePlugin)

lazy val scala212 = "2.12.16"
lazy val scala213 = "2.13.10"

name := "scalatest-csv-table"
scalaVersion := scala213
crossScalaVersions := Seq(scala212, scala213)
version := "1.2.2"
organization := "io.github.akiomik"
homepage := Some(url(s"https://github.com/akiomik/${name.value}"))
scmInfo := Some(
  ScmInfo(
    url(s"https://github.com/akiomik/${name.value}"),
    s"git@github.com:akiomik/${name.value}.git"
  )
)
description := "A scalatest helper for table driven testing with csv"
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
developers := List(
  Developer(
    id = "akiomik",
    name = "Akiomi Kamakura",
    email = "akiomik@gmail.com",
    url = url("https://github.com/akiomik")
  )
)

lazy val kantanCsvVersion = "0.7.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.14",
  "com.nrinaudo" %% "kantan.csv" % kantanCsvVersion,
  "com.nrinaudo" %% "kantan.csv-generic" % kantanCsvVersion
)

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xlint",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard"
)

versionScheme := Some("semver-spec")
mimaPreviousArtifacts := Set(organization.value %% name.value % "1.0.0")

sonatypeCredentialHost := "s01.oss.sonatype.org"
publishTo := sonatypePublishToBundle.value
publishMavenStyle := true

// needed for scalac-scoverage-reporter
evictionErrorLevel := Level.Info
