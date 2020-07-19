import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

val KotlinMultiplatformExtension.kampCommonMain: KampSourceSet get() = sourceSets.getByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME).kampCommon
val KotlinMultiplatformExtension.kampCommonTest: KampSourceSet get() = sourceSets.getByName(KotlinSourceSet.COMMON_TEST_SOURCE_SET_NAME).kampCommon

inline fun KotlinMultiplatformExtension.dependencies(compilation: String, crossinline configure: KampDependencyHandler.() -> Unit) {
    forRealTargets {
        kampSourceSet(compilation).dependencies(configure)
    }
}

inline fun KotlinMultiplatformExtension.dependenciesMain(crossinline configure: KampDependencyHandler.() -> Unit) {
    kampCommonMain.dependencies(configure)
    dependencies("main", configure)
}

inline fun KotlinMultiplatformExtension.dependenciesTest(crossinline configure: KampDependencyHandler.() -> Unit) {
    kampCommonTest.dependencies(configure)
    dependencies("test", configure)
}

@PublishedApi
internal inline fun KotlinMultiplatformExtension.forRealTargets(crossinline configure: KotlinTarget.() -> Unit) {
    targets.all {
        if (it.name != KotlinMultiplatformPlugin.METADATA_TARGET_NAME) it.configure()
    }
}

//@PublishedApi
//internal inline fun KotlinTarget.whenReady(crossinline fn: KotlinTarget.() -> Unit) {
//    when (this) {
//        is KotlinAndroidTarget -> project.whenEvaluated { fn(this@whenReady) }
//        else                   -> fn(this)
//    }
//}
//
//@PublishedApi
//internal inline fun Project.whenEvaluated(crossinline fn: Project.() -> Unit) {
//    when (state.executed) {
//        true -> fn()
//        false -> afterEvaluate { it.fn() }
//    }
//}
