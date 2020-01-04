import dev.whyoleg.kamp.dependency.*
import org.gradle.plugin.use.*

fun PluginDependenciesSpec.use(vararg plugins: KampPlugin) {
    plugins.forEach { id(it.name) }
}

fun PluginDependenciesSpec.use(plugins: Iterable<KampPlugin>) {
    plugins.forEach { id(it.name) }
}
