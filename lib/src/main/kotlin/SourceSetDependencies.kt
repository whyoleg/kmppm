import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.platform.*
import org.jetbrains.kotlin.gradle.plugin.*

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
