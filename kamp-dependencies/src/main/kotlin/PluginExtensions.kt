import org.gradle.plugin.use.*

fun PluginDependenciesSpec.ids(vararg ids: String) {
    ids.forEach { id(it) }
}

fun PluginDependenciesSpec.ids(ids: Iterable<String>) {
    ids.forEach { id(it) }
}
