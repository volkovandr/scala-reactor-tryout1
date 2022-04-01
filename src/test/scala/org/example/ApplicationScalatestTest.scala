package org.example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.PropertyChecks

class ApplicationScalatestTest extends AnyFlatSpec with Matchers with PropertyChecks {

  "Application's hello function" should "return hello" in {
    Application.hello() shouldEqual "hello"
  }

  "scalatest" should "execute a parameterized test with a predefined Table of values" in {
    val vals = Table(("n"), (1), (2), (3))

    forAll (vals) { v =>
      println(v)
      v should be >= 1
    }
  }

  it should "also automatically generate property values" in {
    forAll { (v: Int) =>
      whenever(v >= 0) {
        println(v)
        v should be >= 0
      }
    }
  }

}
