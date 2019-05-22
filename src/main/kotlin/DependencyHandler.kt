package dev.whyoleg.kamp

import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

typealias DependencyConverter = KotlinDependencyHandler.() -> DependencyHandler

interface DependencyHandler : KotlinDependencyHandler {
    fun add(artifact: Any)
}

fun KotlinDependencyHandler.helper(): DependencyHelper = DependencyHelper(this)

class DependencyHelper internal constructor(kotlin: KotlinDependencyHandler) {
    val api: DependencyHandler = api(kotlin)
    val implementation: DependencyHandler = implementation(kotlin)
    val runtimeOnly: DependencyHandler = runtimeOnly(kotlin)
    val compileOnly: DependencyHandler = compileOnly(kotlin)



    companion object {
        private val api: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(artifact: Any) {
                    api(artifact)
                }
            }
        }

        private val implementation: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(artifact: Any) {
                    implementation(artifact)
                }
            }
        }

        private val runtimeOnly: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(artifact: Any) {
                    runtimeOnly(artifact)
                }
            }
        }

        private val compileOnly: DependencyConverter = {
            object : DependencyHandler, KotlinDependencyHandler by this {
                override fun add(artifact: Any) {
                    compileOnly(artifact)
                }
            }
        }
    }
}
