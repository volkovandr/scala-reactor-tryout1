package org.example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.PropertyChecks

class FlatSpectTestStyle extends AnyFlatSpec with Matchers with PropertyChecks with Logging
