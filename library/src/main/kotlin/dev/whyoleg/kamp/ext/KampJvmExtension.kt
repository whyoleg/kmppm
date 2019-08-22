package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.target.configuration.*
import org.jetbrains.kotlin.gradle.dsl.*
import kotlin.reflect.*

@KampDSL
class KampJvmExtension(
    configuration: ProjectConfiguration,
    versions: BuiltInVersions
) : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget, JvmTargetOptions>(
    configuration,
    versions,
    Target.jvm,
    JvmTargetOptions()
) {
    override val extPlugin: Plugin = builtIn.plugins.kotlinJvm
    override val extPluginClass: KClass<KotlinJvmProjectExtension> = KotlinJvmProjectExtension::class
}