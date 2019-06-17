package com.github.akiomik.scalatest

import java.io.IOException
import org.scalatest._
import org.scalatest.prop.TableDrivenPropertyChecks._

class CsvTableSpec extends WordSpecLike with Matchers {

  "A CsvTable" should {
    "throw IOException if csv is empty" in {
      val csv = ""
      an[IOException] should be thrownBy CsvTable.fromString[Int, Int](csv)
    }

    "throw no exception if csv body is empty" in {
      val csv = "1,2"
      noException should be thrownBy CsvTable.fromString[Int, Int](csv)
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

    "create a Table from 1-column csv string" in {
      val expected = Iterator(1, 2)
      val csv =
        """n
          |1
          |2""".stripMargin
      val rows = CsvTable.fromString[Int](csv)

      rows.heading should be("n")
      forAll(rows) { _ should be(expected.next) }
    }

    "create a Table from 2-column csv string" in {
      val expected = Iterator(3, 5)
      val csv =
        """n1,n2
          |1,2
          |2,3""".stripMargin
      val rows = CsvTable.fromString[Int, Int](csv)

      rows.heading should be("n1", "n2")
      forAll(rows) { (n1, n2) =>
        n1 + n2 should be(expected.next)
      }
    }

    "create a Table from 3-column csv string" in {
      val csv =
        """n1,n2,expected
          |1,2,3
          |2,3,5""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "expected")
      forAll(rows) { (n1, n2, expected) =>
        n1 + n2 should be(expected)
      }
    }

    "create a Table from 4-column csv string" in {
      val csv =
        """n1,n2,n3,expected
          |1,2,3,6
          |2,3,4,9""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "expected")
      forAll(rows) { (n1, n2, n3, expected) =>
        n1 + n2 + n3 should be(expected)
      }
    }

    "create a Table from 5-column csv string" in {
      val csv =
        """n1,n2,n3,n4,expected
          |1,2,3,4,10
          |2,3,4,5,14""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "expected")
      forAll(rows) { (n1, n2, n3, n4, expected) =>
        n1 + n2 + n3 + n4 should be(expected)
      }
    }

    "create a Table from 6-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,expected
          |1,2,3,4,5,15
          |2,3,4,5,6,20""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, expected) =>
        n1 + n2 + n3 + n4 + n5 should be(expected)
      }
    }

    "create a Table from 7-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,expected
          |1,2,3,4,5,6,21
          |2,3,4,5,6,7,27""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 should be(expected)
      }
    }

    "create a Table from 8-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,expected
          |1,2,3,4,5,6,7,28
          |2,3,4,5,6,7,8,35""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 should be(expected)
      }
    }

    "create a Table from 9-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,expected
          |1,2,3,4,5,6,7,8,36
          |2,3,4,5,6,7,8,9,44""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 should be(expected)
      }
    }

    "create a Table from 10-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,expected
          |1,2,3,4,5,6,7,8,9,45
          |2,3,4,5,6,7,8,9,10,54""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 should be(expected)
      }
    }

    "create a Table from 11-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,expected
          |1,2,3,4,5,6,7,8,9,10,55
          |2,3,4,5,6,7,8,9,10,11,65""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 should be(expected)
      }
    }

    "create a Table from 12-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,expected
          |1,2,3,4,5,6,7,8,9,10,11,66
          |2,3,4,5,6,7,8,9,10,11,12,77""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 should be(expected)
      }
    }

    "create a Table from 13-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,78
          |2,3,4,5,6,7,8,9,10,11,12,13,90""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 should be(expected)
      }
    }

    "create a Table from 14-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,91
          |2,3,4,5,6,7,8,9,10,11,12,13,14,104""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 should be(expected)
      }
    }

    "create a Table from 15-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,105
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,119""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 should be(expected)
      }
    }

    "create a Table from 16-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,120
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,135""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 should be(expected)
      }
    }

    "create a Table from 17-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,136
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,152""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 should be(expected)
      }
    }

    "create a Table from 18-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,153
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,170""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 should be(expected)
      }
    }

    "create a Table from 19-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,171
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,189""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 should be(expected)
      }
    }

    "create a Table from 20-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,190
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,209""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 should be(expected)
      }
    }

    "create a Table from 21-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,n20,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,210
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,230""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 should be(expected)
      }
    }

    "create a Table from 22-column csv string" in {
      val csv =
        """n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15,n16,n17,n18,n19,n20,n21,expected
          |1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,231
          |2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,252""".stripMargin
      val rows = CsvTable.fromString[Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int, Int](csv)

      rows.heading should be("n1", "n2", "n3", "n4", "n5", "n6", "n7", "n8", "n9", "n10", "n11", "n12", "n13", "n14", "n15", "n16", "n17", "n18", "n19", "n20", "n21", "expected")
      forAll(rows) { (n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15, n16, n17, n18, n19, n20, n21, expected) =>
        n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14 + n15 + n16 + n17 + n18 + n19 + n20 + n21 should be(expected)
      }
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
