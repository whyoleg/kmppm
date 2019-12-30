package dev.whyoleg.kamp.dependencies

import dev.whyoleg.kamp.dependency.*

object GradleBuiltInPlugins {
    val application = KampPlugin("org.gradle.application")
    val mavenPublish = KampPlugin("org.gradle.maven-publish")
    val javaPlugin = KampPlugin("org.gradle.java-gradle-plugin")
}
