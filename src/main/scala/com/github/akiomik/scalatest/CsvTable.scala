package com.github.akiomik.scalatest

import java.io.{File, IOException}
import scala.io.Source

import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import org.scalatest.prop.Tables._

object CsvTable {
  type Header1 = String
  type Header2 = (String, String)
  type Header3 = (String, String, String)
  type Header4 = (String, String, String, String)
  type Header5 = (String, String, String, String, String)
  type Header6 = (String, String, String, String, String, String)
  type Header7 = (String, String, String, String, String, String, String)
  type Header8 = (String, String, String, String, String, String, String, String)
  type Header9 = (String, String, String, String, String, String, String, String, String)
  type Header10 = (String, String, String, String, String, String, String, String, String, String)
  type Header11 = (String, String, String, String, String, String, String, String, String, String, String)
  type Header12 = (String, String, String, String, String, String, String, String, String, String, String, String)
  type Header13 = (String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header14 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header15 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header16 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header17 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header18 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header19 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header20 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header21 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)
  type Header22 = (String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String)

  def validate(csv: String): Unit = {
    if (csv.lines.size < 2)
      throw new IOException("A csv must have 2 lines or more (body with header).")
  }

  def header[A](csv: String)(implicit decoder: RowDecoder[A]): A = {
    csv.lines.next.unsafeReadCsvRow[A](rfc)
  }

  def apply[A](csv: String)(implicit decoder: RowDecoder[A]) = {
    validate(csv)

    Table(
      header[Header1](csv),
      csv.unsafeReadCsv[Seq, A](rfc.withHeader): _*
    )
  }

  def apply[A, B](csv: String)(implicit decoder: RowDecoder[(A, B)]) = {
    validate(csv)

    Table(
      header[Header2](csv),
      csv.unsafeReadCsv[Seq, (A, B)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C](csv: String)(implicit decoder: RowDecoder[(A, B, C)]) = {
    validate(csv)

    Table(
      header[Header3](csv),
      csv.unsafeReadCsv[Seq, (A, B, C)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D](csv: String)(implicit decoder: RowDecoder[(A, B, C, D)]) = {
    validate(csv)

    Table(
      header[Header4](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E)]) = {
    validate(csv)

    Table(
      header[Header5](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F)]) = {
    validate(csv)

    Table(
      header[Header6](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G)]) = {
    validate(csv)

    Table(
      header[Header7](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H)]) = {
    validate(csv)

    Table(
      header[Header8](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I)]) = {
    validate(csv)

    Table(
      header[Header9](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J)]) = {
    validate(csv)

    Table(
      header[Header10](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K)]) = {
    validate(csv)

    Table(
      header[Header11](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L)]) = {
    validate(csv)

    Table(
      header[Header12](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M)]) = {
    validate(csv)

    Table(
      header[Header13](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]) = {
    validate(csv)

    Table(
      header[Header14](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]) = {
    validate(csv)

    Table(
      header[Header15](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]) = {
    validate(csv)

    Table(
      header[Header16](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]) = {
    validate(csv)

    Table(
      header[Header17](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]) = {
    validate(csv)

    Table(
      header[Header18](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]) = {
    validate(csv)

    Table(
      header[Header19](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]) = {
    validate(csv)

    Table(
      header[Header20](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]) = {
    validate(csv)

    Table(
      header[Header21](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)](rfc.withHeader): _*
    )
  }

  def apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](csv: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]) = {
    validate(csv)

    Table(
      header[Header22](csv),
      csv.unsafeReadCsv[Seq, (A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)](rfc.withHeader): _*
    )
  }

  def fromFile[A](name: String)(implicit decoder: RowDecoder[A]) = {
    val s = Source.fromFile(name)
    try apply[A](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B](name: String)(implicit decoder: RowDecoder[(A, B)]) = {
    val s = Source.fromFile(name)
    try apply[A, B](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C](name: String)(implicit decoder: RowDecoder[(A, B, C)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D](name: String)(implicit decoder: RowDecoder[(A, B, C, D)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromFile[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]) = {
    val s = Source.fromFile(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A](name: String)(implicit decoder: RowDecoder[A]) = {
    val s = Source.fromResource(name)
    try apply[A](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B](name: String)(implicit decoder: RowDecoder[(A, B)]) = {
    val s = Source.fromResource(name)
    try apply[A, B](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C](name: String)(implicit decoder: RowDecoder[(A, B, C)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D](name: String)(implicit decoder: RowDecoder[(A, B, C, D)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U](s.getLines.mkString("\n"))
    finally s.close
  }

  def fromResource[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](name: String)(implicit decoder: RowDecoder[(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V)]) = {
    val s = Source.fromResource(name)
    try apply[A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V](s.getLines.mkString("\n"))
    finally s.close
  }
}
