import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

internal inline val Project.kotlin: KotlinMultiplatformExtension get() = extensions.getByType(KotlinMultiplatformExtension::class.java)

inline fun Project.kotlinMPP(crossinline block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(KotlinMultiplatformExtension::class.java) {
        it.block()
    }
}

inline fun KotlinMultiplatformExtension.kotlinOptions(crossinline block: KotlinCommonOptions.() -> Unit) {
    targets.all { target ->
        target.compilations.all { it.kotlinOptions.block() }
    }
}

inline fun KotlinJvmProjectExtension.kotlinOptions(crossinline block: KotlinJvmOptions.() -> Unit) {
    target.compilations.all { it.kotlinOptions.block() }
}

inline fun KotlinProjectExtension.languageSettings(crossinline block: LanguageSettingsBuilder.(sourceSet: KotlinSourceSet) -> Unit) {
    sourceSets.all {
        it.languageSettings.block(it)
    }
}

//fun Project.test() {
//    kotlinMPP {
//        kotlinOptions {
//
//        }
//        val jvm = jvm()
//        val js = jvm()
//
//        listOf(kampCommonMain, jvm.kampSourceSetMain, js.kampSourceSetMain).dependencies {
//
//        }
//
//        kampSourceSetsMain {
//            dependencies {
//
//            }
//        }
//    }
//}
