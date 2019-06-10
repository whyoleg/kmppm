package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.builder.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*

@KampDSL
abstract class KampSinglePlatformExtension<EXTENSION : KotlinProjectExtension, TARGET : PlatformTarget>(
    private val ext: EXTENSION,
    project: Project,
    private val target: TARGET
) : KampExtension<EXTENSION>(ext, project) {

    fun sourceSet(builder: SourceSetConfigurationBuilder<TARGET>.() -> Unit) {
        sources += Source(
            MultiTarget(target.name, target::class, setOf(target)),
            SourceSetConfigurationBuilder<TARGET>().apply(builder).data()
        )
    }

    override fun configureTargets() {
        targets += target
    }

    override fun sourceTypeTargets(sourceType: SourceType): Map<Target, KotlinSourceSet> =
        mapOf(target to ext.sourceSets.maybeCreate(sourceType.name))
}