package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.base.PlatformTarget
import dev.whyoleg.kamp.base.Target
import dev.whyoleg.kamp.builder.KampDSL
import dev.whyoleg.kamp.builder.SourceBuilder
import dev.whyoleg.kamp.builder.SourceType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

@KampDSL
class KampMultiplatformExtension(
    private val ext: KotlinMultiplatformExtension
) : KampExtension<KotlinMultiplatformExtension>(ext) {
    public override val targets: MutableSet<PlatformTarget> = mutableSetOf()

    fun sourceSets(builder: SourceBuilder.() -> Unit) {
        sources += SourceBuilder().apply(builder).sources
    }

    override fun configureTargets() {
        (targets + Target.common).forEach { it.configure(ext, it) }
    }

    override fun sourceTypeTargets(sourceType: SourceType): Map<Target, KotlinSourceSet> =
        (targets + Target.common).associateWith { ext.sourceSets.maybeCreate(it.name + sourceType.name.capitalize()) }

}
