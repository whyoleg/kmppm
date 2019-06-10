package dev.whyoleg.kamp.builder

import com.github.jengelman.gradle.plugins.shadow.tasks.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*
import org.gradle.api.plugins.*

@Suppress("UnstableApiUsage")
class JarPackaging : Packaging {
    var className: String? = null
    var name: String? = null
    var minimize: Boolean = false

    override val plugins: Set<Plugin> = with(BuiltInPlugins) { setOf(shadow, application) }

    override fun Project.configure() {
        val plainName = path.replace(":", "-").drop(1)
        val packageName = plainName.replace("-", ".")
        val name = this@JarPackaging.name ?: plainName
        val className = this@JarPackaging.className ?: "$group.$packageName.AppKt"
        extensions.configure<JavaApplication>("application") { it.mainClassName = className }
        tasks.withType(ShadowJar::class.java) {
            it.baseName = name
            it.version = this.version.toString()
            it.classifier = null
            if (minimize) it.minimize()
        }
    }
}