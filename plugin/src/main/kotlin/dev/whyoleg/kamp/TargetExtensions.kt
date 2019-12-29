package dev.whyoleg.kamp

import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.platform.*
import org.gradle.api.artifacts.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

interface KampDependencyHandler {
    fun api(dependencyNotation: KampDependency): Dependency?
    fun api(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency?
}

@PublishedApi
internal class PlatformKampDependencyHandler(
    private val platformType: KotlinPlatformType,
    private val handler: KotlinDependencyHandler
) : KampDependencyHandler {
    override fun api(dependencyNotation: KampDependency): Dependency? = dependencyNotation.notation(platformType)?.let(handler::api)

    override fun api(dependencyNotation: KampDependency, configure: ExternalModuleDependency.() -> Unit): ExternalModuleDependency? =
        dependencyNotation.notation(platformType)?.let { handler.api(it, configure) }
}

inline fun List<KotlinTarget>.dependenciesMain(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("main", configure)
}

inline fun List<KotlinTarget>.dependenciesTest(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("test", configure)
}

inline fun List<KotlinTarget>.dependencies(compilation: String, crossinline configure: KampDependencyHandler.() -> Unit) {
    forEach {
        it.dependencies(compilation, configure)
    }
}

inline fun KotlinTarget.dependenciesMain(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("main", configure)
}

inline fun KotlinTarget.dependenciesTest(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("test", configure)
}

inline fun KotlinTarget.dependencies(compilation: String, crossinline configure: KampDependencyHandler.() -> Unit) {
    compilations.getByName(compilation).defaultSourceSet.dependencies(platformType, configure)
}

inline fun List<KotlinSourceSet>.dependencies(platform: KampPlatform, crossinline configure: KampDependencyHandler.() -> Unit) {
    forEach {
        it.dependencies(platform, configure)
    }
}

inline fun KotlinSourceSet.dependencies(platform: KampPlatform, crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies(platform.platformType, configure)
}

inline fun KotlinSourceSet.dependencies(platformType: KotlinPlatformType, crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies {
        PlatformKampDependencyHandler(platformType, this).run(configure)
    }
}

fun KotlinMultiplatformExtension.test() {
    listOf(jvm(), js()).dependencies("main") {

    }
    listOf(jvm(), js()).dependenciesMain {
        api(BuiltInDependencies.kotlin.stdlib)
        api(BuiltInDependencies.kotlin.stdlib) {

        }
    }
    jvm("jvm6") {
        dependencies("main") {

        }
    }
    val jvmMain = sourceSets.create("jvmMain")
    val jsMain = sourceSets.create("jsMain")
    listOf(jvmMain, jsMain).dependencies(KampPlatform.Common) {

    }
}
