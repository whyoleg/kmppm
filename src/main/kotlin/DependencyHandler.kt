package dev.whyoleg.kamp

import dev.whyoleg.kamp.base.Artifact
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.artifact
import dev.whyoleg.kamp.builder.DependencySetType
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

internal interface DependencyHandler : KotlinDependencyHandler {
    fun add(artifact: Any)
}

operator fun KotlinDependencyHandler.set(type: DependencySetType, dependencies: Iterable<Artifact<out Target>>) {
    val handler = when (type) {
        DependencySetType.implementation -> Helper.implementation(this)
        DependencySetType.api -> Helper.api(this)
        DependencySetType.runtimeOnly -> Helper.runtimeOnly(this)
        DependencySetType.compileOnly -> Helper.compileOnly(this)
    }
    dependencies.forEach { handler.artifact(it) }
}

private typealias DependencyConverter = KotlinDependencyHandler.() -> DependencyHandler

private object Helper {
    val api: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                api(artifact)
            }
        }
    }

    val implementation: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                implementation(artifact)
            }
        }
    }

    val runtimeOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                runtimeOnly(artifact)
            }
        }
    }

    val compileOnly: DependencyConverter = {
        object : DependencyHandler, KotlinDependencyHandler by this {
            override fun add(artifact: Any) {
                compileOnly(artifact)
            }
        }
    }
}