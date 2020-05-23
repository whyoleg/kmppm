package dev.whyoleg.kamp.feature.kotlin

import dev.whyoleg.kamp.dependency.*

object KotlinGroup : Group by group("org.jetbrains.kotlin") {
    fun stdlib(version: String = Kotlin.defaultVersion): KampDependency =
        artifact("kotlin-stdlib").version(version).platforms(
            KampPlatform.common("common"),
            KampPlatform.jvm(),
            KampPlatform.android(),
            KampPlatform.js("js")
        )

    fun plugin(version: String = Kotlin.defaultVersion): KampDependency =
        artifact("kotlin-gradle-plugin").version(version).platforms(KampPlatform.jvm)

}
