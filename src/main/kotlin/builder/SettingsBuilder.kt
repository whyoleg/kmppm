package dev.whyoleg.kamp.builder

import dev.whyoleg.kamp.*

class SettingsBuilder {
    var languageVersion: String = "1.3"
    var apiVersion: String = "1.3"
    var progressiveMode: Boolean = false
    val languageFeatures: MutableSet<LanguageFeature> = mutableSetOf()
    val experimentalAnnotations: MutableSet<ExperimentalAnnotation> = mutableSetOf()

    val plainLanguageFeatures: MutableSet<String> = mutableSetOf()
    val plainExperimentalAnnotations: MutableSet<String> = mutableSetOf()

    internal val allFeatures: Set<String> get() = languageFeatures.map(LanguageFeature::value).toSet() + plainLanguageFeatures
    internal val allAnnotations: Set<String> get() = experimentalAnnotations.map(ExperimentalAnnotation::value).toSet() + plainExperimentalAnnotations
}