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
class KampJvmExtension : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget, JvmTargetOptions>(Target.jvm, JvmTargetOptions()) {
    override val extPlugin: Plugin = BuiltInPlugins.kotlinJvm
    override val extPluginClass: KClass<KotlinJvmProjectExtension> = KotlinJvmProjectExtension::class
}