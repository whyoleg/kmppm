import dev.whyoleg.kamp.feature.gradle.*
import dev.whyoleg.kamp.feature.kotlin.*

object Plugins {
    val kotlin = listOf(Kotlin.plugins.jvm, Gradle.plugins.mavenPublish)
}
