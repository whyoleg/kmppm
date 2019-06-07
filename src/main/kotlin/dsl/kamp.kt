package dev.whyoleg.kamp.dsl

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.reflect.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
fun Project.kamp(block: KampMultiPlatformExtension.() -> Unit): Unit = internalKamp(block)

@KampDSL
fun Project.kampJvm(block: KampJvmExtension.() -> Unit): Unit = internalKamp(block)


@Suppress("UNCHECKED_CAST")
internal inline fun <reified Ext : KotlinProjectExtension, KampExt : KampExtension<Ext>> Project.internalKamp(
    crossinline block: KampExt.() -> Unit
) {
    apply {
        it.plugin(
            when (Ext::class) {
                KotlinMultiplatformExtension::class -> "org.jetbrains.kotlin.multiplatform"
                KotlinJvmProjectExtension::class    -> "org.jetbrains.kotlin.jvm"
                else                                -> error("Platform is not supported")
            }
        )
    }
    println(getKotlinPluginVersion())
    extensions.configure(object : TypeOf<Ext>() {}) {
        (when (it) {
            is KotlinMultiplatformExtension -> (KampMultiPlatformExtension(it) as KampExt).apply(block)
            is KotlinJvmProjectExtension    -> (KampJvmExtension(it) as KampExt).apply(block)
            else                            -> error("Platform is not supported")
        }).configure()
    }
}
