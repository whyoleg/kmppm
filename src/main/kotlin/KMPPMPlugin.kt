package dev.whyoleg.kmppm

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

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
    apply<KotlinMultiplatformPluginWrapper>()
    extensions.configure<KotlinMultiplatformExtension> {
        KMPPMExtension(this).apply(block)
    }
}
