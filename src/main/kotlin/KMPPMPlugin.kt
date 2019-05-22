package dev.whyoleg.kmppm

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.reflect.TypeOf
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.getKotlinPluginVersion

class KMPPMPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@MagicDSL
class KMPPMExtension(val kotlin: KotlinMultiplatformExtension) {
    fun kotlin(block: KotlinMultiplatformExtension.() -> Unit): Unit = kotlin.block()
}

@MagicDSL
fun Project.mppm(block: KMPPMExtension.() -> Unit) {
    println("HELLO")
    apply { it.plugin("org.jetbrains.kotlin.jvm") }
    println(this.getKotlinPluginVersion())
    extensions.configure(object : TypeOf<KotlinMultiplatformExtension>() {}) {
        KMPPMExtension(it).apply(block)
    }
}
