import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.dependency.*

object Plugins {
    val pluginModule = listOf(KotlinPlugins.stable.kotlinJvm, GradleBuiltInPlugins.javaPlugin)
}
