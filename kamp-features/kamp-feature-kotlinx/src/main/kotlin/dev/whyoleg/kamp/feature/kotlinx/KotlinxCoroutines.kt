package dev.whyoleg.kamp.feature.kotlinx

import dev.whyoleg.kamp.dependency.*

class KotlinxCoroutines(version: String = KotlinxVersions.Default.coroutines) : KampDependency by KotlinxGroup.coroutines(version) {
    private val jvm: GroupWithVersionPlatforms = platforms(KampPlatform.jvm)

    val test = jvm.artifact("kotlinx-coroutines-test")
    val debug = jvm.artifact("kotlinx-coroutines-debug")

    //ui
    val android = artifact("kotlinx-coroutines-android").platforms(KampPlatform.android)
    val javaFX = jvm.artifact("kotlinx-coroutines-javafx")
    val swing = jvm.artifact("kotlinx-coroutines-swing")

    //integrations
    val slf4j = jvm.artifact("kotlinx-coroutines-slf4j")
    val playServices = jvm.artifact("kotlinx-coroutines-play-services")
    val guava = jvm.artifact("kotlinx-coroutines-guava")
    val jdk8 = jvm.artifact("kotlinx-coroutines-jdk8")

    //reactive
    val reactive = jvm.artifact("kotlinx-coroutines-reactive")
    val reactor = jvm.artifact("kotlinx-coroutines-reactor")
    val rx2 = jvm.artifact("kotlinx-coroutines-rx2")
}
