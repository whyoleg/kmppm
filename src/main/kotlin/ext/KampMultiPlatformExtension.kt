package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.source.*
import dev.whyoleg.kamp.sourceset.*
import dev.whyoleg.kamp.target.*
import dev.whyoleg.kamp.target.Target
import org.jetbrains.kotlin.gradle.dsl.*
import org.jetbrains.kotlin.gradle.plugin.*
import kotlin.reflect.*

@KampDSL
class KampMultiPlatformExtension : KampExtension<KotlinMultiplatformExtension>() {
    override val extPlugin: Plugin = BuiltInPlugins.kotlinMpp
    override val extPluginClass: KClass<KotlinMultiplatformExtension> = KotlinMultiplatformExtension::class

    fun targets(vararg targets: PlatformTarget) {
        this.targets += targets
    }

    fun targets(targets: Iterable<PlatformTarget>) {
        this.targets += targets
    }

    fun sourceSets(builder: SourceBuilder.() -> Unit) {
        sources += SourceBuilder().apply(builder).sources
    }

    override fun configureTargets(ext: KotlinMultiplatformExtension) {
        (targets + Target.common).forEach { it.provider(ext) }
    }

    override fun sourceTypeTargets(ext: KotlinMultiplatformExtension, sourceType: SourceSetType): Map<Target, KotlinSourceSet> =
        (targets + Target.common).associateWith { ext.sourceSets.maybeCreate(it.name + sourceType.name.capitalize()) }

}
