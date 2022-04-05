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
      (s: Subscription) => s.request(5)
    )

    StepVerifier.create(mono)
      .expectNext(name.toUpperCase)
      .verifyComplete()
  }

  "Mono" should "work with doOn methods" in {
    val name = "Mono Test 6"
    val mono = Mono.just(name)
      .map[String](_.toUpperCase)
      .doOnSubscribe(_ => log.info("Subscribed"))
      .doOnRequest(n => log.info(s"Request received, ${n}"))
      .doOnNext(s => log.info(s"Next1: ${s}"))
      .flatMap[String](_ => Mono.empty[String]())
      .doOnNext(s => log.info(s"Next2: ${s}"))
      .doOnSuccess(s => log.info(s"Success: ${s}"))

    mono.subscribe(
      (s: String) => log.info(s"Got from Mono: $s "),
      log.error("Mono error", _),
      () => log.info("Mono complete"),
      (s: Subscription) => s.request(5)
    )

    StepVerifier.create(mono)
      .verifyComplete()
  }

  "Mono" should "work with doOnError methods" in {
    val mono = Mono.error[String](new IllegalArgumentException("Ooops"))
      .doOnError(e => log.error(s"Mono error ${e.getMessage}"))
      .log()

    mono.subscribe(
      (s: String) => log.info(s"Received from Mono: ${s}"),
      (e: Throwable) => log.error(s"Recevied error from Mono: ${e.getMessage}")
    )

    StepVerifier.create(mono)
      .expectError(classOf[IllegalArgumentException])
      .verify()
  }

  "Mono" should "work with doOnErrorResume methods" in {
    val name = "Mono test 8"
    val mono = Mono.error[String](new IllegalArgumentException("Ooops"))
      .doOnError(e => log.error(s"Mono error ${e.getMessage}"))
      .onErrorResume(e => {
        log.error(s"Inside onErrorResume, error: ${e.getMessage}")
        Mono.just(name)
      })
      .log()

    mono.subscribe(
      (s: String) => log.info(s"Received from Mono: ${s}"),
      (e: Throwable) => log.error(s"Recevied error from Mono: ${e.getMessage}")
    )

    StepVerifier.create(mono)
      .expectNext(name)
      .verifyComplete()
  }
}
