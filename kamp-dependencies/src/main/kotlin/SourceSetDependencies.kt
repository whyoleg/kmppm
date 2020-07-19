import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.plugin.*

fun KotlinSourceSet.kamp(platform: KampPlatform): KampSourceSet = KampSourceSet(this, platform)

val KotlinSourceSet.kampCommon: KampSourceSet get() = kamp(KampPlatform.common)
val KotlinSourceSet.kampJvm: KampSourceSet get() = kamp(KampPlatform.jvm)
val KotlinSourceSet.kampAndroid: KampSourceSet get() = kamp(KampPlatform.android)
val KotlinSourceSet.kampJs: KampSourceSet get() = kamp(KampPlatform.js)
val KotlinSourceSet.kampNative: KampSourceSet get() = kamp(KampPlatform.native)


inline fun KampSourceSet.dependencies(crossinline configure: KampDependencyHandler.() -> Unit) {
    sourceSet.dependencies {
        KampPlatformDependencyHandler(platform.platformType, this).run(configure)
    }
}

inline fun Iterable<KampSourceSet>.dependencies(crossinline configure: KampDependencyHandler.() -> Unit) {
    forEach {
        it.dependencies(configure)
    }
}
