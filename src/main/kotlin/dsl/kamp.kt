package dev.whyoleg.kamp.dsl

import dev.whyoleg.kamp.ext.*
import org.gradle.api.initialization.*

fun Settings.kamp(block: KampSettings.() -> Unit) {
    KampSettings(this).apply(block).configure()
}
