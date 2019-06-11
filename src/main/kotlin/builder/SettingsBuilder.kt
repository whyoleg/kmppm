package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.*

class SettingsBuilder {
    var languageVersion: String = "1.3"
    var apiVersion: String = "1.3"
    var progressiveMode: Boolean = false
    val languageFeatures: MutableSet<LanguageFeature> = mutableSetOf()
    val experimentalAnnotations: MutableSet<ExperimentalAnnotation> = mutableSetOf()
}