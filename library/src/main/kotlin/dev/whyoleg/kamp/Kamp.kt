package dev.whyoleg.kamp

import dev.whyoleg.kamp.configuration.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.initialization.*

internal const val defaultVersionsKind = "default"

@KampDSL
fun Settings.kamp(block: KampSettings.() -> Unit): Unit = KampSettings().apply(block).configure(this)

@KampDSL
fun Project.kampBuild(
    versionsKind: String = defaultVersionsKind,
    block: KampBuild.() -> Unit
): Unit = KampBuild().apply(block).configure(versionsKind, this)

@KampDSL
fun Project.kampRoot(block: KampRoot.() -> Unit): Unit = KampRoot().apply(block).configure(this)

@KampDSL
fun Project.kamp(
    configuration: ProjectConfiguration? = null,
    versionsKind: String = defaultVersionsKind,
    block: KampMultiPlatformExtension.() -> Unit
): Unit = KampMultiPlatformExtension(configuration).apply(block).configure(versionsKind, this)

@KampDSL
fun Project.kampJvm(
    configuration: ProjectConfiguration? = null,
    versionsKind: String = defaultVersionsKind,
    block: KampJvmExtension.() -> Unit
): Unit = KampJvmExtension(configuration).apply(block).configure(versionsKind, this)
