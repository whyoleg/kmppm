package dev.whyoleg.kamp

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.plugin.*
import org.gradle.api.*
import org.gradle.api.reflect.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import org.gradle.api.Plugin as GradlePlugin

class KampPlugin : GradlePlugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        val ext = extensions.create("kamp", KampPluginExtension::class.java)
        println("START")
        afterEvaluate {
            println("CONFIG with: ${ext.configurations}")
            ext.configurations.forEach { it() }
        }
    }
}

open class KampPluginExtension {

    internal val configurations = mutableListOf<Project.() -> Unit>()

    @KampDSL
    fun mpp(block: KampMultiPlatformExtension.() -> Unit): Unit = internalKamp(block)

    @KampDSL
    fun jvm(block: KampJvmExtension.() -> Unit): Unit = internalKamp(block)

    @Suppress("UNCHECKED_CAST")
    internal inline fun <reified Ext : KotlinProjectExtension, KampExt : KampExtension<Ext>> internalKamp(
        crossinline block: KampExt.() -> Unit
    ) {
        configurations += {
            apply {
                it.plugin(
                    when (Ext::class) {
                        KotlinMultiplatformExtension::class -> PluginName.kotlinMpp
                        KotlinJvmProjectExtension::class    -> PluginName.kotlinJvm
                        else                                -> error("Platform is not supported")
                    }
                )
            }
            println()
            println("Setup plugin with kotlin ${getKotlinPluginVersion()}")
            println()
            extensions.configure(object : TypeOf<Ext>() {}) {
                (when (it) {
                    is KotlinMultiplatformExtension -> (KampMultiPlatformExtension(it, this) as KampExt).apply(block)
                    is KotlinJvmProjectExtension    -> (KampJvmExtension(it, this) as KampExt).apply(block)
                    else                            -> error("Platform is not supported")
                }).configure()
            }
        }
    }
}