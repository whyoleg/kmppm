package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.configuration.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import dev.whyoleg.kamp.target.configuration.*
import org.jetbrains.kotlin.gradle.dsl.*
import kotlin.reflect.*

@KampDSL
class KampJvmExtension(
    configuration: ProjectConfiguration
) : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget, JvmTargetOptions>(configuration, Target.jvm, JvmTargetOptions()) {
    override val extPlugin: Plugin = BuiltInPlugins.kotlinJvm
    override val extPluginClass: KClass<KotlinJvmProjectExtension> = KotlinJvmProjectExtension::class
}
