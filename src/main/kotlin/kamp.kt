package dev.whyoleg.kamp

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.ext.*
import org.gradle.api.*
import org.gradle.api.initialization.*

@KampDSL
fun Settings.kamp(block: KampSettings.() -> Unit): Unit = KampSettings().apply(block).configure(this)

@KampDSL
fun Project.kampBuild(block: KampBuild.() -> Unit): Unit = KampBuild().apply(block).configure(this)

@KampDSL
fun Project.kampRoot(block: KampRoot.() -> Unit): Unit = KampRoot().apply(block).configure(this)

@KampDSL
fun Project.kamp(block: KampMultiPlatformExtension.() -> Unit): Unit = KampMultiPlatformExtension().apply(block).configure(this)

@KampDSL
fun Project.kampJvm(block: KampJvmExtension.() -> Unit): Unit = KampJvmExtension().apply(block).configure(this)
