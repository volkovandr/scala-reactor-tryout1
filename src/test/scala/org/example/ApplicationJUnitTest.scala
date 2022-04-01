package org.example

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.scalatest.matchers.should.Matchers

class ApplicationJUnitTest extends Matchers {

  @Test
  def `hello should return hello`(): Unit = {
    Application.hello() shouldEqual "hello"
  }

  @ParameterizedTest
  @ValueSource(ints = Array(1,2,3))
  def `hello should work 3 times`(number: Int): Unit = {
    println(s"Test $number")
  }
}
