package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.JvmTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.builder.SourceSetConfigurationBuilder
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reflect.TypeOf
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

class KampPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@KampDSL
fun Project.kamp(block: KampExtension.() -> Unit) {
    apply { it.plugin("org.jetbrains.kotlin.multiplatform") }
    println(this.getKotlinPluginVersion())
    extensions.configure(object : TypeOf<KotlinMultiplatformExtension>() {}) {
        KampExtension(it).apply(block).configure()
    }
}

@KampDSL
fun Project.kampJvm(block: SourceSetConfigurationBuilder<JvmTarget>.() -> Unit) {
    apply { it.plugin("org.jetbrains.kotlin.kotlin") }
    println(this.getKotlinPluginVersion())
    extensions.configure(object : TypeOf<KotlinMultiplatformExtension>() {}) {
        KampExtension(it).apply {
            targets += Target.jvm
            sourceSets { Target.jvm(block) }
        }.configure()
    }
}
