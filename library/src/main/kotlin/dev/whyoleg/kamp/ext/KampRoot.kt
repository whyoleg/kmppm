package dev.whyoleg.kamp.ext

import com.github.benmanes.gradle.versions.updates.*
import com.gradle.scan.plugin.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*

@Suppress("UnstableApiUsage")
class KampRoot {

    private val plugins: MutableSet<Plugin> = mutableSetOf()
    private val buildScans = mutableListOf<BuildScanExtension.() -> Unit>()

    fun plugins(vararg plugins: Plugin) {
        this.plugins += plugins
    }

    fun plugins(plugins: Iterable<Plugin>) {
        this.plugins += plugins
    }

    private val rejects: MutableSet<(RawDependency) -> Boolean> = mutableSetOf()

    fun rejectUpdates(block: (RawDependency) -> Boolean) {
        rejects += block
    }

    fun buildScan(block: BuildScanExtension.() -> Unit) {
        buildScans += block
    }

    internal fun configure(project: Project) {
        if (rejects.isNotEmpty()) plugins += BuiltInPlugins.updates
        if (buildScans.isNotEmpty()) plugins += BuiltInPlugins.buildScan
        project.repositories.apply { plugins.forEach { it.classpath?.provider?.invoke(this) } }
        project.apply { plugins.forEach { plugin -> it.plugin(plugin.name) } }

        if (rejects.isNotEmpty()) project.tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java) {
            it.resolutionStrategy {
                it.componentSelection {
                    it.all { component ->
                        val candidate = component.candidate
                        val raw = RawDependency(candidate.group, candidate.module, { candidate.version }, null)
                        rejects.forEach { if (it(raw)) component.reject("") }
                    }
                }
            }
        }

        if (buildScans.isNotEmpty()) project.extensions.configure<BuildScanExtension>("buildScan") { buildScan ->
            buildScans.forEach { buildScan.it() }
        }
    }
}
