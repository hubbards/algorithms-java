// https://docs.gradle.org/current/userguide/userguide.html

import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  `java-library`
}

repositories {
  jcenter()
}

dependencies {
  implementation(group = "com.google.guava", name = "guava", version = "26.0-jre")
  testImplementation(group = "junit", name = "junit", version = "4.12")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
  useJUnit()

  testLogging {
    events.add(TestLogEvent.FAILED)
    events.add(TestLogEvent.SKIPPED)
  }
}
