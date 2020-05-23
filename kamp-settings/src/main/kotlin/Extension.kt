import dev.whyoleg.kamp.settings.*
import org.gradle.api.initialization.*
import java.io.*

fun Settings.kamp(block: KampSettings.() -> Unit) {
    KampSettings().apply(block).apply {
        root?.apply {
            modules().forEach { (name, path) ->
                include(name)
                project(":$name").projectDir = rootDir.resolve(path)
            }
            writeToBuildSrc(clsName, lines())
        }
        versionsClsName?.let { clsName ->
            writeToBuildSrc(clsName, extensions.extraProperties.properties.parseVersions(clsName))
        }
    }
}

private fun Settings.writeToBuildSrc(name: String, data: List<String>) {
    rootDir.resolve("buildSrc/src/main/kotlin")
        .also(File::mkdirs)
        .resolve("$name.kt")
        .writeText(data.joinToString("\n"))
}

val String.prefixedModule get() = PrefixedModuleFactory(this)
val String.prefixedFolder get() = PrefixedFolderFactory(this)
