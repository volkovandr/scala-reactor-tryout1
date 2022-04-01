group = "org.example"
version = "1.0-SNAPSHOT"

val scalaVersion = "2.13.8"
val reactorVersion = "3.4.16"
val junitVersion = "5.8.2"

plugins {
    scala
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:$scalaVersion")

    implementation("io.projectreactor:reactor-core:$reactorVersion")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    testImplementation("io.projectreactor:reactor-test:$reactorVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")

    testImplementation("co.helmethair:scalatest-junit-runner:0.1.10")
    testImplementation("org.scalatest:scalatest_2.13:3.3.0-SNAP3")
}

tasks.test {
    useJUnitPlatform()
}