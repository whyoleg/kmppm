package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.base.JvmTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.builder.KampDSL
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

@KampDSL
class KampJvmExtension(
    ext: KotlinJvmProjectExtension
) : KampPlatformExtension<KotlinJvmProjectExtension, JvmTarget>(ext, Target.jvm)