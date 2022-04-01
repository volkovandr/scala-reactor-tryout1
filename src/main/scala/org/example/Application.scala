package org.example

object Application extends App with Logging {
  log.info(s"${hello()}")

  def hello(): String = "hello"
}


