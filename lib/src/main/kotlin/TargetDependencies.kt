import dev.whyoleg.kamp.dependency.*
import org.jetbrains.kotlin.gradle.plugin.*

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
