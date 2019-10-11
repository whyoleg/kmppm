package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.plugin.Plugin
import dev.whyoleg.kamp.version.*
import org.gradle.api.*

class KampBuild internal constructor(applySelf: Boolean) {
    private val plugins: MutableSet<Plugin> = if (applySelf) mutableSetOf(BuiltInPlugins.kamp) else mutableSetOf()
    private val dependencies: MutableSet<RawDependency> = mutableSetOf()
    private val versionsMap: MutableMap<String, BuiltInVersions> = mutableMapOf()

    fun dependencies(vararg dependencies: RawDependency) {
        this.dependencies += dependencies
    }

    fun dependencies(dependencies: Iterable<RawDependency>) {
        this.dependencies += dependencies
    }

    fun resolvePlugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun resolvePlugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    fun registerDefaultVersions(versions: BuiltInVersions) {
        registerVersions { default(versions) }
    }

    fun registerVersions(block: VersionsBuilder.() -> Unit) {
        val builder = VersionsBuilder().apply(block)
        versionsMap += builder.versionsMap
    }

    internal fun configure(versionsKind: String, project: Project) {
        if (versionsMap.isEmpty()) versionsMap += defaultVersionsKind to BuiltInVersions.Stable
        val buildVersions = versionsMap[versionsKind.toLowerCase()] ?: noVersionsRegistered(versionsKind)
        versionsMap.forEach { (kind, versions) ->
            project.writeVersions(kind, versions)
        }
        val deps = dependencies + plugins.mapNotNull(Plugin::classpath)
        project.repositories.let { handler ->
            deps.mapNotNull(RawDependency::provider).forEach { it(handler) }
        }
        project.dependencies.let { handler ->
            deps.map { it.string(buildVersions) }.forEach { handler.add("implementation", it) }
        }
    }
}
