import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.modules.*
import org.jetbrains.kotlin.config.*

plugins {
    use(Plugins.pluginModule)
}

kotlin {
    target {
        dependenciesMain {
            implementation(KotlinDependencies.Stable.gradlePlugin)
            compileOnly(BuiltInDependencies.Stable.shadow)
        }
        options {
            progressive()
            languageVersion = "1.3"
            apiVersion = "1.3"
            jvmTarget = "1.6"
            enableLanguageFeatures(listOf(LanguageFeature.InlineClasses, LanguageFeature.NewInference))
            useExperimentalAnnotations(KotlinExperimentalAnnotations.All)
        }
    }
}

jvmPublication(publication, publisher.provider(publish = false)) {
    artifactId = "kamp"
}

createBintrayPublishTask(publisher)
