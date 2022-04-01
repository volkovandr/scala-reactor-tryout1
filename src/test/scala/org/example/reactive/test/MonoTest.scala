package org.example.reactive.test

import org.example.FlatSpectTestStyle
import org.reactivestreams.Subscription
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoTest extends FlatSpectTestStyle {

  "Mono subscriber" should "work" in {
    val name = "Mono Test 1"
    val mono = Mono.just(name).log()

    mono.subscribe()

    StepVerifier.create(mono)
      .expectNext(name)
      .verifyComplete()

    log.info(s"Mono: $mono")
  }

  "Mono consumer" should "work" in {
    val name = "Mono Test 2"
    val mono = Mono.just(name).log()

    mono.subscribe((s: String) => log.info(s"Got from Mono: $s "))

    StepVerifier.create(mono)
      .expectNext(name)
      .verifyComplete()
  }

  it should "work when there is an error" in {
    val name = "Mono Test 3"
    val mono = Mono.just(name)
      .map((_: String) => throw new RuntimeException("Testing Mono with Error"))

    mono.subscribe((_: String) => log.info("Mono onNext"), (e: Throwable) => log.error("Mono error", e))

    StepVerifier.create(mono)
      .expectError(classOf[RuntimeException])
  }

  "Mono consumer" should "work with Complete" in {
    val name = "Mono Test 4"
    val mono = Mono.just(name)
      .log()
      .map[String](_.toUpperCase)
      .log()

    mono.subscribe(
      (s: String) => log.info(s"Got from Mono: $s "),
      log.error("Mono error", _),
      () => log.info("Mono complete")
    )

    StepVerifier.create(mono)
      .expectNext(name.toUpperCase)
      .verifyComplete()
  }

  "Mono consumer" should "work with Subscription" in {
    val name = "Mono Test 5"
    val mono = Mono.just(name)
      .log()
      .map[String](_.toUpperCase)
      .log()

    mono.subscribe(
      (s: String) => log.info(s"Got from Mono: $s "),
      log.error("Mono error", _),
      () => log.info("Mono complete"),
      (s: Subscription) => s.cancel()
    )

    StepVerifier.create(mono)
      .expectNext(name.toUpperCase)
      .verifyComplete()
  }
}
