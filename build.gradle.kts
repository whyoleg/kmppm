import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dev.whyoleg.kamp"
version = "0.1.0"

plugins {
    id(Plugin.updates) version Version.updates
    kotlin("jvm") version Version.kotlin
    `java-gradle-plugin`
    `maven-publish`
}

repositories { mavenCentral() }

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
}

tasks.withType<KotlinCompile> {
    sourceCompatibility = Version.jdk
    targetCompatibility = Version.jdk
    kotlinOptions.jvmTarget = Version.jdk
    kotlinOptions {
        apiVersion = Version.kotlinApi
        languageVersion = Version.kotlinLanguage
        freeCompilerArgs = listOf("-progressive", "-XXLanguage:+InlineClasses", "-XXLanguage:+NewInference")
    }
}
