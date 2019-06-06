package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.base.PlatformTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.base.TargetSet
import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.builder.Source
import dev.whyoleg.kamp.builder.SourceSetConfigurationBuilder
import dev.whyoleg.kamp.builder.SourceType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@KampDSL
abstract class KampPlatformExtension<EXTENSION : KotlinProjectExtension, TARGET : PlatformTarget>(
    private val ext: EXTENSION,
    private val target: TARGET
) : KampExtension<EXTENSION>(ext) {

    fun sourceSet(builder: SourceSetConfigurationBuilder<TARGET>.() -> Unit) {
        sources += Source(
            TargetSet(target.name, target::class, setOf(target)),
            SourceSetConfigurationBuilder(setOf(target)).apply(builder).data()
        )
    }

    override fun configureTargets() {
        targets += target
    }

    override fun sourceTypeTargets(sourceType: SourceType): Map<Target, KotlinSourceSet> =
        mapOf(target to ext.sourceSets.maybeCreate(sourceType.name))
}