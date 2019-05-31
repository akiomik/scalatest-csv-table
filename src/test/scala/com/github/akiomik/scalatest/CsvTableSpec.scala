package com.github.akiomik.scalatest

import java.io.IOException
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._

class CsvTableSpec extends WordSpecLike with Matchers {

  "A CsvTable" should {
    "throw IOException if csv is less than 2 lines" in {
      val csv = "1,2"
      an [IOException] should be thrownBy CsvTable[Int, Int](csv)
    }

    "decode Int values in csv" in {
      val expected = Iterator(1, 2)
      val rows = CsvTable.fromResource[Int]("int.csv")

      forAll(rows) { _ should be(expected.next) }
    }

    "decode Double values in csv" in {
      val expected = Iterator(42, 3.14)
      val rows = CsvTable.fromResource[Double]("double.csv")

      forAll(rows) { _ should be(expected.next) }
    }

    "decode Boolean values in csv" in {
      val expected = Iterator(true, false)
      val rows = CsvTable.fromResource[Boolean]("boolean.csv")

      forAll(rows) { _ should be(expected.next) }
    }

    "decode String values in csv" in {
      val expected = Iterator("", "foo", "foo bar", "foo\nbar\nbaz")
      val rows = CsvTable.fromResource[String]("string.csv")

      forAll(rows) { _ should be(expected.next) }
    }

    "decode Option[String] values in csv" in {
      // TODO: support optional single column
      val expected = Iterator(None, Some("foo"))
      val rows = CsvTable.fromResource[Int, Option[String]]("option.csv")

      forAll(rows) { (_: Int, opt: Option[String]) =>
        opt should be(expected.next)
      }
    }

    "decode values in csv as a case class" in {
      import kantan.csv._

      case class Foo(s: String, i: Int, b: Boolean)
      implicit val decoder = RowDecoder.decoder(0, 1, 2)(Foo.apply _)

      val expected = Iterator(Foo("foo", 0, true), Foo("bar", -42, false))
      val rows = CsvTable.fromFile[Foo]("src/test/resources/case-class.csv")

      forAll(rows) { _ should be(expected.next) }
    }

    "create a Table from 1-column csv file" in {
      val expected = Iterator(1, 2)
      val rows = CsvTable.fromFile[Int]("src/test/resources/01.csv")

      rows.heading should be("n")
      forAll(rows) { _ should be(expected.next) }
    }

    "create a Table from 2-column csv file" in {
      val expected = Iterator(3, 5)
      val rows = CsvTable.fromFile[Int, Int]("src/test/resources/02.csv")

      rows.heading should be("n1", "n2")
      forAll(rows) { (n1, n2) =>
        n1 + n2 should be(expected.next)
      }
    }

    "create a Table from 3-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int](
          "src/test/resources/03.csv"
        )

      rows.heading should be("n1", "n2", "expected")
      forAll(rows) { (n1, n2, expected) =>
        n1 + n2 should be(expected)
      }
    }

    "create a Table from 4-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int](
          "src/test/resources/04.csv"
        )

      rows.heading should be("n1", "n2", "n3", "expected")
      forAll(rows) { (n1, n2, n3, expected) =>
        n1 + n2 + n3 should be(expected)
      }
    }

    "create a Table from 5-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int](
          "src/test/resources/05.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "expected")
      forAll(rows) { (n1, n2, n3, n4, expected) =>
        n1 + n2 + n3 + n4 should be(expected)
      }
    }

    "create a Table from 6-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int](
          "src/test/resources/06.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, expected) =>
        n1 + n2 + n3 + n4 + n5 should be(expected)
      }
    }

    "create a Table from 7-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/07.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 should be(expected)
      }
    }

    "create a Table from 8-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/08.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 should be(expected)
      }
    }

    "create a Table from 9-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/09.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 should be(expected)
      }
    }

    "create a Table from 10-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/10.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 should be(expected)
      }
    }

    "create a Table from 11-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/11.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 should be(expected)
      }
    }

    "create a Table from 12-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/12.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 should be(expected)
      }
    }

    "create a Table from 13-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/13.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 should be(expected)
      }
    }

    "create a Table from 14-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/14.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 should be(expected)
      }
    }

    "create a Table from 15-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/15.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 should be(expected)
      }
    }

    "create a Table from 16-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/16.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 should be(
          expected
        )
      }
    }

    "create a Table from 17-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/17.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 should be(
          expected
        )
      }
    }

    "create a Table from 18-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/18.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 should be(expected)
      }
    }

    "create a Table from 19-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/19.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 should be(expected)
      }
    }

    "create a Table from 20-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/20.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 should be(expected)
      }
    }

    "create a Table from 21-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/21.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 should be(expected)
      }
    }

    "create a Table from 22-column csv file" in {
      val rows =
        CsvTable.fromFile[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "src/test/resources/22.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "n21", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, n21, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 + n21 should be(expected)
      }
    }

    "create a Table from 1-column csv file in resources" in {
      val expected = Iterator(1, 2)
      val rows = CsvTable.fromResource[Int]("01.csv")

      rows.heading should be("n")
      forAll(rows) { _ should be(expected.next) }
    }

    "create a Table from 2-column csv file in resources" in {
      val expected = Iterator(3, 5)
      val rows = CsvTable.fromResource[Int, Int]("02.csv")

      rows.heading should be("n1", "n2")
      forAll(rows) { (n1, n2) =>
        n1 + n2 should be(expected.next)
      }
    }

    "create a Table from 3-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int](
          "03.csv"
        )

      rows.heading should be("n1", "n2", "expected")
      forAll(rows) { (n1, n2, expected) =>
        n1 + n2 should be(expected)
      }
    }

    "create a Table from 4-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int](
          "04.csv"
        )

      rows.heading should be("n1", "n2", "n3", "expected")
      forAll(rows) { (n1, n2, n3, expected) =>
        n1 + n2 + n3 should be(expected)
      }
    }

    "create a Table from 5-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int](
          "05.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "expected")
      forAll(rows) { (n1, n2, n3, n4, expected) =>
        n1 + n2 + n3 + n4 should be(expected)
      }
    }

    "create a Table from 6-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int](
          "06.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, expected) =>
        n1 + n2 + n3 + n4 + n5 should be(expected)
      }
    }

    "create a Table from 7-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int](
          "07.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 should be(expected)
      }
    }

    "create a Table from 8-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int](
          "08.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 should be(expected)
      }
    }

    "create a Table from 9-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "09.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 should be(expected)
      }
    }

    "create a Table from 10-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "10.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 should be(expected)
      }
    }

    "create a Table from 11-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "11.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 should be(expected)
      }
    }

    "create a Table from 12-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "12.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 should be(expected)
      }
    }

    "create a Table from 13-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "13.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 should be(expected)
      }
    }

    "create a Table from 14-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "14.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 should be(expected)
      }
    }

    "create a Table from 15-column csv file in resouces" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "15.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 should be(expected)
      }
    }

    "create a Table from 16-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "16.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 should be(
          expected
        )
      }
    }

    "create a Table from 17-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "17.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 should be(
          expected
        )
      }
    }

    "create a Table from 18-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "18.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 should be(expected)
      }
    }

    "create a Table from 19-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "19.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 should be(expected)
      }
    }

    "create a Table from 20-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "20.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 should be(expected)
      }
    }

    "create a Table from 21-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "21.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 should be(expected)
      }
    }

    "create a Table from 22-column csv file in resources" in {
      val rows =
        CsvTable.fromResource[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](
          "22.csv"
        )

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "n21", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, n21, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 + n21 should be(expected)
      }
    }

  }

}
