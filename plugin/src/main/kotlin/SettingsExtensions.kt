import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.module.*
import org.gradle.api.initialization.*
import java.io.*

inline fun Settings.modules(block: ModuleContext.() -> Unit) {
    val (modules, cls) = resolveModules(block)
    modules.forEach { (name, path) ->
        include(name)
        path?.let { project(name).projectDir = rootDir.resolve(it) }
    }
    rootDir.resolve("buildSrc/src/main/kotlin").also(File::mkdirs).resolve("Modules.kt").writeText(cls)
}

fun Settings.resolvePlugins(vararg plugins: KampPlugin) {
    val pms = pluginManagement
    val repositories = pms.repositories
    val pluginSpec = pms.plugins
    plugins.forEach { (name, classpath) ->
        classpath?.let {
            it.provider?.invoke(repositories)
            pluginSpec.id(name).version(it.version)
        }
    }
}
