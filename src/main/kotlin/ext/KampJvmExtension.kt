package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*

@KampDSL
class KampJvmExtension(
    ext: KotlinJvmProjectExtension
) : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget>(ext, Target.jvm)