package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.base.target.*
import dev.whyoleg.kamp.base.target.Target
import dev.whyoleg.kamp.builder.*
import org.jetbrains.kotlin.gradle.dsl.*

@KampDSL
class KampJvmExtension(
    ext: KotlinJvmProjectExtension
) : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget>(ext, Target.jvm)