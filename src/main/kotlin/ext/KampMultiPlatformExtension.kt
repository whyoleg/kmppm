package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
class KampMultiPlatformExtension(
    private val ext: KotlinMultiplatformExtension,
    project: Project
) : KampExtension<KotlinMultiplatformExtension>(ext, project) {
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
