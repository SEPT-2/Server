val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val ktor_version: String by project

plugins {
  kotlin("jvm") version "2.0.0"
  id("io.ktor.plugin") version "2.3.12"
  id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "example.com"
version = "0.0.1"

application {
  mainClass.set("io.ktor.server.netty.EngineMain")

  val isDevelopment: Boolean = project.ext.has("development")
  applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
  mavenCentral()
}

dependencies {
  // Ktor server dependencies
  implementation("io.ktor:ktor-server-core-jvm")
  implementation("io.ktor:ktor-server-webjars-jvm")
  implementation("io.ktor:ktor-server-content-negotiation-jvm")
  implementation("io.ktor:ktor-server-call-logging-jvm")
  implementation("io.ktor:ktor-server-host-common-jvm")
  implementation("io.ktor:ktor-server-status-pages-jvm")
  implementation("io.ktor:ktor-server-resources-jvm")
  implementation("io.ktor:ktor-server-netty-jvm")
  implementation("io.ktor:ktor-server-config-yaml")
  implementation("io.ktor:ktor-server-cors-jvm")
  implementation("io.ktor:ktor-server-request-validation")

  // Serialization dependencies
  implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
  implementation("io.ktor:ktor-serialization-jackson-jvm")

  // Database and ORM dependencies
  implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
  implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")

  // SQLite driver
  implementation("org.xerial:sqlite-jdbc:3.46.0.0")


  // Logging dependencies
  implementation("ch.qos.logback:logback-classic:$logback_version")

  // Swagger UI and jQuery dependencies
  implementation("org.webjars:jquery:3.2.1")
  implementation("io.github.smiley4:ktor-swagger-ui:2.9.0")
  implementation("io.swagger.core.v3:swagger-annotations:2.2.20")

  // Ktor Client
  implementation("io.ktor:ktor-client-core-jvm")
  implementation("io.ktor:ktor-client-cio-jvm")
  implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

  // Koin for Ktor
  implementation("io.insert-koin:koin-ktor:3.5.3")
  runtimeOnly("io.insert-koin:koin-core:3.5.3")

  // Test dependencies
  testImplementation("io.ktor:ktor-server-tests-jvm")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}
