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
