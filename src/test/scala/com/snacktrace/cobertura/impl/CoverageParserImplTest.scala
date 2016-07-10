package com.snacktrace.cobertura.impl

import java.util

import org.scalatest.WordSpec

/**
  * Created by ultmast on 9/07/16.
  */
class CoverageParserImplTest extends WordSpec {
  trait Fixture {
    val parser = new CoberturaParserImpl()
  }

  "CoverageParser" should {
    "parse coverage" in new Fixture {
      val result = parser.parse(getClass.getResourceAsStream("/coverage.xml"))
      println(result)
    }
  }
}
