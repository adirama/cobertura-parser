package com.snacktrace.cobertura.impl

import java.util

import com.snacktrace.cobertura.impl.CoberturaParserImpl
import org.scalatest.WordSpec

/**
  * Created by ultmast on 9/07/16.
  */
class CoverageParserTest extends WordSpec {
  trait Fixture {
  }

  "CoverageParser" should {
    "parse coverage" in new Fixture {
      val paths = new util.HashSet[String]()
      val result = new CoberturaParserImpl().parse(getClass.getResourceAsStream("/coverage.xml"))
      println(result)
    }
  }
}
