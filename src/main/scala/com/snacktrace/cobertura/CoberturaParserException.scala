package com.snacktrace.cobertura

/**
  * Created by ultmast on 9/07/16.
  */
final case class CoberturaParserException(message: String, cause: Option[Throwable]) extends Exception(message, cause.orNull)
