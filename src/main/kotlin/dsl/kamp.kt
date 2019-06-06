package dev.whyoleg.kamp.dsl

import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.ext.KampExtension
import dev.whyoleg.kamp.ext.KampJvmExtension
import dev.whyoleg.kamp.ext.KampMultiplatformExtension
import org.gradle.api.Project
import org.gradle.api.reflect.TypeOf
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

@KampDSL
fun Project.kamp(block: KampMultiplatformExtension.() -> Unit): Unit = internalKamp(block)

@KampDSL
fun Project.kampJvm(block: KampJvmExtension.() -> Unit): Unit = internalKamp(block)


internal inline fun <reified Ext : KotlinProjectExtension, KampExt : KampExtension<Ext>> Project.internalKamp(
    crossinline block: KampExt.() -> Unit
) {
    apply {
        it.plugin(
            when (Ext::class) {
                KotlinMultiplatformExtension::class -> "org.jetbrains.kotlin.multiplatform"
                KotlinJvmProjectExtension::class -> "org.jetbrains.kotlin.jvm"
                else -> error("Platform is not supported")
            }
        )
    }
    println(getKotlinPluginVersion())
    extensions.configure(object : TypeOf<Ext>() {}) {
        (when (it) {
            is KotlinMultiplatformExtension -> (KampMultiplatformExtension(it) as KampExt).apply(block)
            is KotlinJvmProjectExtension -> (KampJvmExtension(it) as KampExt).apply(block)
            else -> error("Platform is not supported")
        }).configure()
    }
}
