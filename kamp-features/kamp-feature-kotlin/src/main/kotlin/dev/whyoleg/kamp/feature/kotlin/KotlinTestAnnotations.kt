package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

class KotlinTestAnnotations(version: String = Kotlin.defaultVersion) {
    private val group = KotlinGroup.version(version)
    private fun test(name: String, platform: KampPlatform): KampDependency = group.artifact("kotlin-test-$name").platforms(platform)

    val common = test("annotations-common", KampPlatform.common)
    val junit = test("junit", KampPlatform.jvm)
    val junit5 = test("junit5", KampPlatform.jvm)
    val testng = test("testng", KampPlatform.jvm)
}
