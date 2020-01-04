package dev.whyoleg.kamp.options

inline class LanguageFeature(val value: String) {
    companion object {
        val InlineClasses = LanguageFeature("InlineClasses")
        val NewInference = LanguageFeature("NewInference")
    }
}

inline class CompilerArgument(val value: String) {
    companion object {
        val AllowResultReturnType = CompilerArgument("allow-result-return-type")
    }
}

inline class ExperimentalAnnotation(val value: String)
