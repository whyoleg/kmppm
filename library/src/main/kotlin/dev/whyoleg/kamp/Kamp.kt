package dev.whyoleg.kamp

import dev.whyoleg.kamp.configuration.*
import dev.whyoleg.kamp.ext.*
import dev.whyoleg.kamp.version.*
import org.gradle.api.*
import org.gradle.api.initialization.*

private val defaultVersionsKind = BuiltInVersionsKind.Default.name.toLowerCase()
@KampDSL
fun Settings.kamp(block: KampSettings.() -> Unit): Unit = KampSettings().apply(block).configure(this)

@KampDSL
fun Project.kampBuild(
    versionsKind: String = defaultVersionsKind,
    block: KampBuild.() -> Unit
): Unit = KampBuild(true).apply(block).configure(versionsKind, this)

@KampDSL
fun Project.kampRoot(block: KampRoot.() -> Unit): Unit = KampRoot().apply(block).configure(this)

@KampDSL
fun Project.kamp(
    configuration: ProjectConfiguration,
    versionsKind: String = defaultVersionsKind,
    block: KampMultiPlatformExtension.() -> Unit
): Unit = KampMultiPlatformExtension(configuration).apply(block).configure(versionsKind, this)

@KampDSL
fun Project.kampJvm(
    configuration: ProjectConfiguration,
    versionsKind: String = defaultVersionsKind,
    block: KampJvmExtension.() -> Unit
): Unit = KampJvmExtension(configuration).apply(block).configure(versionsKind, this)

@Experimental
annotation class KampInternal

@Deprecated("")
@KampInternal
@KampDSL
fun Project.kampBuildDev(
    versionsKind: String = BuiltInVersionsKind.Default.name,
    block: KampBuild.() -> Unit
) {
    val files = files("../library/build/classes/kotlin/main")
    val localBuildExists = files.singleFile.exists()
    if (localBuildExists) dependencies.add("implementation", (files("../library/build/classes/kotlin/main")))
    KampBuild(!localBuildExists).apply(block).configure(versionsKind, this)
}
