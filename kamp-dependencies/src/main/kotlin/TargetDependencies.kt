import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.plugin.*

inline fun KotlinTarget.dependencies(compilation: String, crossinline configure: KampDependencyHandler.() -> Unit) {
    compilations.findByName(compilation)?.defaultSourceSet?.dependencies(platformType, configure)
}

inline fun Iterable<KotlinTarget>.dependencies(compilation: String, crossinline configure: KampDependencyHandler.() -> Unit) {
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

inline fun Iterable<KotlinTarget>.dependenciesMain(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("main", configure)
}

inline fun Iterable<KotlinTarget>.dependenciesTest(crossinline configure: KampDependencyHandler.() -> Unit) {
    dependencies("test", configure)
}
