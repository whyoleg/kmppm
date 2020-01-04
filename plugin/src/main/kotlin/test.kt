import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.modules.*
import dev.whyoleg.kamp.options.*
import dev.whyoleg.kamp.platform.*
import dev.whyoleg.kamp.publication.*
import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.test() {
    val kotlin = KotlinModule(KotlinVersion.Stable)
    val apiProject = KampProjectDependency("123")

    listOf(jvm(), js()).dependencies("main") {
        implementation(apiProject) {

        }
    }
    listOf(jvm(), js()).dependenciesMain {
        with(kotlin.dependencies) {
            api(stdlib)
            api(reflect) {

            }
        }
    }
    jvm("jvm6") {
        dependencies("main") {

        }
    }
    val jvmMain = sourceSets.create("jvmMain")
    val jsMain = sourceSets.create("jsMain")
    listOf(jvmMain, jsMain).dependencies(KampPlatform.common) {

    }
    publication(Publication("", ""))
    metadata {

    }
    jvm {
        val s2 = optionsMain()
        optionsTest {

        }
    }
    targets.all {
        it.commonOptionsMain {
            enableLanguageFeatures(listOf(LanguageFeature.InlineClasses))
            useExperimentalAnnotations(
                listOf(
                    KotlinExperimentalAnnotations.ExperimentalContracts,
                    KotlinExperimentalAnnotations.ExperimentalTime
                )
            )
            progressive()
        }
    }

    jvmMain.languageSettings.apiVersion = "1"
    sourceSets.all {
        it.languageSettings.apiVersion = "2"
        it.languageSettings.apply {
            enableLanguageFeatures(listOf(LanguageFeature.InlineClasses))
            useExperimentalAnnotations(
                listOf(
                    KotlinExperimentalAnnotations.ExperimentalContracts,
                    KotlinExperimentalAnnotations.ExperimentalTime
                )
            )
        }
    }
}
