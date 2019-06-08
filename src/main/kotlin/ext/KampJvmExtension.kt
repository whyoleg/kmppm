package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*

@KampDSL
class KampJvmExtension(
    ext: KotlinJvmProjectExtension,
    project: Project
) : KampSinglePlatformExtension<KotlinJvmProjectExtension, JvmTarget>(ext, project, Target.jvm)