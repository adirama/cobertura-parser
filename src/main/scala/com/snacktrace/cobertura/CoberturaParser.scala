package com.snacktrace.cobertura

import java.io.InputStream

/**
  * Created by ultmast on 9/07/16.
  */
trait CoberturaParser {
  def parse(stream: InputStream): Coverage
}
