package dev.whyoleg.kamp.settings

data class LanguageSettings(
    var languageVersion: String = "1.3",
    var apiVersion: String = "1.3",
    var progressiveMode: Boolean = false,
    var allWarningsAsErrors: Boolean = false,
    var suppressWarnings: Boolean = false,
    val languageFeatures: MutableSet<LanguageFeature> = mutableSetOf(),
    val experimentalAnnotations: MutableSet<ExperimentalAnnotation> = mutableSetOf(),
    val compilerArguments: MutableSet<CompilerArgument> = mutableSetOf(),
    val plainLanguageFeatures: MutableSet<String> = mutableSetOf(),
    val plainExperimentalAnnotations: MutableSet<String> = mutableSetOf(),
    val plainCompilerArguments: MutableSet<String> = mutableSetOf()
) {
    internal val allLanguageFeatures: Set<String> get() = languageFeatures.map(LanguageFeature::value).toSet() + plainLanguageFeatures
    internal val allExperimentalAnnotations: Set<String> get() = experimentalAnnotations.map(ExperimentalAnnotation::value).toSet() + plainExperimentalAnnotations
    internal val allCompilerArguments: Set<String> get() = compilerArguments.map(CompilerArgument::value).toSet() + plainCompilerArguments
}