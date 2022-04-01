group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    scala
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.8")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.junit.platform:junit-platform-runner:1.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")

    testImplementation("co.helmethair:scalatest-junit-runner:0.1.10")
    testImplementation("org.scalatest:scalatest_2.13:3.3.0-SNAP3")
}

tasks.test {
    useJUnitPlatform()
}