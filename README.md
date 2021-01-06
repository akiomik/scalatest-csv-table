scalatest-csv-table
===================
[![Download](https://api.bintray.com/packages/akiomik/maven/scalatest-csv-table/images/download.svg)](https://bintray.com/akiomik/maven/scalatest-csv-table/_latestVersion)

A [scalatest](http://www.scalatest.org/) helper for loading csv files as [`Table`](http://www.scalatest.org/user_guide/table_driven_property_checks).

## Getting started

scalatest-csv-table is currently available for Scala 2.11 and 2.12.

Add the following lines to your `build.sbt`.

```scala
resolvers += Resolver.jcenterRepo

libraryDependencies += "com.github.akiomik" %% "scalatest-csv-table" % "1.0.1" % Test
```

## Basic usage

```scala
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks._

import com.github.akiomik.scalatest._

class FizzBuzzSpec extends FlatSpec {

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
import org.scalatest.FlatSpec
import org.scalatest.prop.TableDrivenPropertyChecks._
import kantan.csv._

case class Foo(i: Int, s: String, b: Boolean)

class FooSpec extends FlatSpec {
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
