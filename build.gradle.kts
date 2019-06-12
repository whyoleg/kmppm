import org.jetbrains.kotlin.gradle.tasks.*

group = "dev.whyoleg.kamp"
version = "0.1.0"

plugins {
    id("com.github.ben-manes.versions") version "0.21.0"
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
}

repositories {
    jcenter()
    google()
    mavenCentral()
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.40-eap-105")
    compileOnly("com.github.jengelman.gradle.plugins:shadow:5.0.0")
    compileOnly("com.github.ben-manes:gradle-versions-plugin:0.21.0")
    compileOnly("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:1.3.0")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.0-RC15")
}

tasks.withType<KotlinCompile> {
    val jdkVersion = "1.8"
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
    kotlinOptions.jvmTarget = jdkVersion
    kotlinOptions {
        apiVersion = "1.3"
        languageVersion = "1.3"
        freeCompilerArgs = listOf("-progressive", "-XXLanguage:+InlineClasses", "-XXLanguage:+NewInference")
    }
}
