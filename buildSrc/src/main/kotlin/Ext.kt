import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.publishing.*
import dev.whyoleg.kamp.settings.*
import org.gradle.api.*

val configuration = ProjectConfiguration("dev.whyoleg.kamp", "kamp") {
    "0.1.4"
//    "0.1.4-${versioning.info.build}"
}

@KampDSL
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

val publication = Publication(
    name = "kamp",
    description = "Gradle plugin for kotlin MPP",
    licenses = listOf(License("Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0.txt", "repo")),
    developers = listOf(Developer("whyoleg", "Oleg", "whyoleg@gmail.com")),
    labels = listOf("Kotlin", "MPP", "Plugin"),
    scmConnections = "scm:git:git@github.com:whyoleg/kamp.git",
    vcsUrl = "git@github.com:whyoleg/kamp.git",
    websiteUrl = "https://github.com/whyoleg/kamp",
    githubUrl = "https://github.com/whyoleg/kamp"
)