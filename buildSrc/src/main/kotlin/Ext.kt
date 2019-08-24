import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.settings.*
import org.gradle.api.*

val configuration = ProjectConfiguration("dev.whyoleg.kamp", "kamp") { "0.1.3" }

fun Project.kampJvm(block: KampJvmExtension.() -> Unit) = kampJvm(configuration) {
    options {
        jvmTarget = Versions.jdk
        sourceCompatibility = Versions.jdk
        targetCompatibility = Versions.jdk
    }
    languageSettings {
        progressiveMode = true
        languageFeatures += LanguageFeature.values()
        experimentalAnnotations += ExperimentalAnnotation.values()
    }
    apply(block)
}