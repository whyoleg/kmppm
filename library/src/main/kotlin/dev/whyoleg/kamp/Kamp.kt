package dev.whyoleg.kamp

import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.initialization.*


@KampDSL
fun Settings.kamp(block: KampSettings.() -> Unit): Unit = KampSettings().apply(block).configure(this)

@KampDSL
fun Project.kampBuild(versions: BuiltInVersions = BuiltInVersions(), block: KampBuild.() -> Unit): Unit =
    KampBuild().apply(block).configure(versions, this)

@KampDSL
fun Project.kampRoot(block: KampRoot.() -> Unit): Unit = KampRoot().apply(block).configure(this)

@KampDSL
fun Project.kamp(configuration: ProjectConfiguration, block: KampMultiPlatformExtension.() -> Unit): Unit =
    KampMultiPlatformExtension(configuration).apply(block).configure(this)

@KampDSL
fun Project.kampJvm(configuration: ProjectConfiguration, block: KampJvmExtension.() -> Unit): Unit =
    KampJvmExtension(configuration).apply(block).configure(this)
