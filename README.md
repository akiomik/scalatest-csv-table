scalatest-csv-table
===================
[![Latest version](https://index.scala-lang.org/akiomik/scalatest-csv-table/scalatest-csv-table/latest.svg?color=blue&style=flat)](https://index.scala-lang.org/akiomik/scalatest-csv-table/scalatest-csv-table)
[![Scala CI](https://github.com/akiomik/scalatest-csv-table/workflows/Scala%20CI/badge.svg)](https://github.com/akiomik/scalatest-csv-table/actions?query=workflow%3A%22Scala+CI%22)
[![Test Coverage](https://api.codeclimate.com/v1/badges/9f38579ddc03f2c6e5e8/test_coverage)](https://codeclimate.com/github/akiomik/scalatest-csv-table/test_coverage)

A [scalatest](http://www.scalatest.org/) helper for loading csv files as [`Table`](http://www.scalatest.org/user_guide/table_driven_property_checks).

## Getting started

scalatest-csv-table is currently available for Scala 2.12 and 2.13.

Add the following lines to your `build.sbt`.

```scala
libraryDependencies += "io.github.akiomik" %% "scalatest-csv-table" % "1.2.0" % Test
```

NOTE: The groupid has been changed from `com.github.akiomik` to `io.github.akiomik` because the maven repository has been changed from bintray to sonatype.

## All releases

scalatest-csv-table supports some different versions of scalatest.

| scalatest-csv-table version | scalatest version | scala version |
| --------------------------- | ----------------- | ------------- |
| 1.0.2                       | 3.0.x             | 2.11.x/2.12.x |
| 1.1.0                       | 3.1.x             | 2.12.x/2.13.x |
| 1.2.0                       | 3.2.x             | 2.12.x/2.13.x |

## Basic usage

```scala
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks._

import com.github.akiomik.scalatest._

class FizzBuzzSpec extends AnyFlatSpec {

  "A FizzBuzz" should "pass tests from a string" in {
    val csv =
      """n,expected
        |1,1
        |2,2
        |3,Fizz
        |4,4
        |5,Buzz""".stripMargin

    val tests = CsvTable.fromString[Int, String](csv)
    forAll (tests) { (n: Int, expected: String) => 
      assert(FizzBuzz(n) == expected)
    }
  }

  "A FizzBuzz" should "pass tests from a file" in {
    val tests = CsvTable.fromFile[Int, String]("src/test/resources/fizzbuzz.csv")
    forAll (tests) { (n: Int, expected: String) => 
      assert(FizzBuzz(n) == expected)
    }
  }

  "A FizzBuzz" should "pass tests from a resource file" in {
    val tests = CsvTable.fromResource[Int, String]("fizzbuzz.csv") // from `src/test/resouces`
    forAll (tests) { (n: Int, expected: String) => 
      assert(FizzBuzz(n) == expected)
    }
  }
}
```

## Using case classes

Use [`RowDecoder` of kantan.csv](https://nrinaudo.github.io/kantan.csv/rows_as_case_classes.html).

```scala
import com.github.akiomik.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks._
import kantan.csv._

case class Foo(i: Int, s: String, b: Boolean)

class FooSpec extends AnyFlatSpec {
  implicit val decoder = RowDecoder.decoder(0, 1, 2)(Foo.apply _) //

  "A Foo" should "pass tests from a string" in {
    val csv =
      """i,s,b
        |1,a,true
        |2,b,true
        |3,f,false
        |4,e,false
        |5,e,true""".stripMargin

    val tests = CsvTable.fromString[Foo](csv)
    forAll (tests) { (foo: Foo) => 
      // ...
    }
  }
}
```
