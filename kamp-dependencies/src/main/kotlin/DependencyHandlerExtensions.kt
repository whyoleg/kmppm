import dev.whyoleg.kamp.dependency.*
import org.gradle.api.artifacts.*

@JvmName("use2")
inline infix fun Iterable<KampDependency>.via(block: (KampDependency) -> Dependency?): Unit = forEach { block(it) }
inline infix fun Iterable<KampModule>.via(block: (KampModule) -> Dependency?): Unit = forEach { block(it) }

@JvmName("use2")

inline infix fun Array<out KampDependency>.via(block: (KampDependency) -> Dependency?): Unit = forEach { block(it) }
inline infix fun Array<out KampModule>.via(block: (KampModule) -> Dependency?): Unit = forEach { block(it) }


fun KampDependencyHandler.api(modules: Iterable<KampModule>): Unit = modules via ::api
fun KampDependencyHandler.api(vararg modules: KampModule): Unit = modules via ::api

@JvmName("api2")
fun KampDependencyHandler.api(modules: Iterable<KampDependency>): Unit = modules via ::api
fun KampDependencyHandler.api(vararg modules: KampDependency): Unit = modules via ::api


fun KampDependencyHandler.implementation(modules: Iterable<KampModule>): Unit = modules via ::implementation
fun KampDependencyHandler.implementation(vararg modules: KampModule): Unit = modules via ::implementation

@JvmName("implementation2")
fun KampDependencyHandler.implementation(modules: Iterable<KampDependency>): Unit = modules via ::implementation
fun KampDependencyHandler.implementation(vararg modules: KampDependency): Unit = modules via ::implementation


fun KampDependencyHandler.compileOnly(modules: Iterable<KampModule>): Unit = modules via ::compileOnly
fun KampDependencyHandler.compileOnly(vararg modules: KampModule): Unit = modules via ::compileOnly

@JvmName("compileOnly2")
fun KampDependencyHandler.compileOnly(modules: Iterable<KampDependency>): Unit = modules via ::compileOnly
fun KampDependencyHandler.compileOnly(vararg modules: KampDependency): Unit = modules via ::compileOnly


fun KampDependencyHandler.runtimeOnly(modules: Iterable<KampModule>): Unit = modules via ::runtimeOnly
fun KampDependencyHandler.runtimeOnly(vararg modules: KampModule): Unit = modules via ::runtimeOnly

@JvmName("runtimeOnly2")
fun KampDependencyHandler.runtimeOnly(modules: Iterable<KampDependency>): Unit = modules via ::runtimeOnly
fun KampDependencyHandler.runtimeOnly(vararg modules: KampDependency): Unit = modules via ::runtimeOnly
