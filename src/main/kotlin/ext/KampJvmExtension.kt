package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*
import kotlin.reflect.*

@KampDSL
class KampJvmExtension : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget>(Target.jvm) {
    override val extPlugin: Plugin = BuiltInPlugins.kotlinJvm
    override val extPluginClass: KClass<KotlinJvmProjectExtension> = KotlinJvmProjectExtension::class
}