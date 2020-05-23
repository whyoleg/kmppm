package dev.whyoleg.kamp.build

import dependenciesMain
import dev.whyoleg.kamp.feature.kamp.*
import dev.whyoleg.kamp.feature.kotlin.*
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*

class KampBuildPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = target.run {
        val kotlin = extensions.getByType(KotlinJvmProjectExtension::class.java)
        val extension = KampBuildExtension()
        extensions.add("kamp", extension)
        afterEvaluate {
            kotlin.target.dependenciesMain {
                implementation(KampDependencies.dependencies)
                implementation(KotlinPlugin(KotlinVersion.CURRENT.toString()))
                if (extension.publication) implementation(KampDependencies.publication)
                extension.features.forEach { implementation(it.dependency) }
            }
        }
    }
}
