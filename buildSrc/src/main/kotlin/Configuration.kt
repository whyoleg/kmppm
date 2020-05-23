import org.gradle.api.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.*

inline fun Project.configureKotlin(
    artifactId: String? = null,
    gradleApi: Boolean = false,
    crossinline block: KotlinJvmProjectExtension.() -> Unit = {}
) {
    extensions.configure<KotlinJvmProjectExtension> {
        sourceSets.getByName("main") {
            languageSettings.apply {
                languageVersion = "1.3"
                apiVersion = "1.3"
                enableLanguageFeature("NewInference")
                enableLanguageFeature("InlineClasses")
            }
        }
        target {
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.6"
                    languageVersion = "1.3"
                    apiVersion = "1.3"
                }
            }
            dependenciesMain {
                api(Dependencies.kotlin.stdlib)
            }
        }
        apply(block)
    }
    if (artifactId != null) configurePublication(artifactId)
    if (gradleApi) dependencies { "api"(gradleApi()) }
}

inline fun Project.configureFeature(artifactId: String? = null, crossinline block: KotlinJvmProjectExtension.() -> Unit = {}) {
    configureKotlin(artifactId?.let { "feature-$it" }) {
        apply(block)
        target.dependenciesMain {
            api(KampModules.dependencies)
        }
    }
}
