package dev.whyoleg.kamp

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

@MagicDSL
class KampExtension(val kotlin: KotlinMultiplatformExtension) {
    fun kotlin(block: KotlinMultiplatformExtension.() -> Unit): Unit = kotlin.block()
}

@MagicDSL
fun Project.kamp(block: KampExtension.() -> Unit) {
    apply { it.plugin("org.jetbrains.kotlin.multiplatform") }
    println(this.getKotlinPluginVersion())
    extensions.configure(object : TypeOf<KotlinMultiplatformExtension>() {}) {
        KampExtension(it).apply(block)
    }
}
