import org.gradle.api.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.*

inline fun Project.configureKotlin(
    artifactId: String? = null,
    gradleApi: Boolean = false,
    crossinline block: KotlinJvmProjectExtension.() -> Unit = {}
) {
    configureDefault(block)
    if (artifactId != null) configurePublication(artifactId, kampPublisher)
    if (gradleApi) dependencies { "api"(gradleApi()) }
}

inline fun Project.configureFeature(artifactId: String? = null, crossinline block: KotlinJvmProjectExtension.() -> Unit = {}) {
    configureDefault {
        target.kampSourceSetMain.dependencies {
            api(KampModules.dependencies)
        }
        apply(block)
    }
    if (artifactId != null) configurePublication("feature-$artifactId", featurePublisher)
}

@PublishedApi
internal inline fun Project.configureDefault(crossinline block: KotlinJvmProjectExtension.() -> Unit = {}) {
    extensions.configure<KotlinJvmProjectExtension> {
        languageSettings {
            languageVersion = "1.3"
            apiVersion = "1.3"
            enableLanguageFeature("NewInference")
            enableLanguageFeature("InlineClasses")
        }
        kotlinOptions {
            jvmTarget = "1.6"
            languageVersion = "1.3"
            apiVersion = "1.3"
        }
        target.kampSourceSetMain.dependencies {
            api(Dependencies.kotlin.stdlib)
        }
        apply(block)
    }
}
