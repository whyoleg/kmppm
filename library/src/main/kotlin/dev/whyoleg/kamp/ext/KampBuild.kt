package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

class KampBuild {

    private val plugins: MutableSet<Plugin> = mutableSetOf(BuiltInPlugins.kamp)
    private val dependencies: MutableSet<RawDependency> = mutableSetOf()

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

    //TODO may be add plugins block if needed

    internal fun configure(versions: BuiltInVersions, project: Project) {
        project.writeVersions(versions)
        val deps = dependencies + plugins.mapNotNull(Plugin::classpath)
        project.repositories.let { handler ->
            deps.mapNotNull(RawDependency::provider).forEach { it(handler) }
        }
        project.dependencies.let { handler ->
            deps.map { it.string(versions) }.forEach { handler.add("implementation", it) }
        }
    }
}