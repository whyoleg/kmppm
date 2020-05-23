import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.plugin.*

inline fun KotlinSourceSet.dependencies(platformType: KotlinPlatformType, crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies {
        KampPlatformDependencyHandler(platformType, this).run(configure)
    }
}

inline fun KotlinSourceSet.dependencies(platform: KampPlatform, crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies(platform.platformType, configure)
}

inline fun Iterable<KotlinSourceSet>.dependencies(platform: KampPlatform, crossinline configure: KampDependencyHandler.() -> Unit) {
    forEach {
        it.dependencies(platform, configure)
    }
}
