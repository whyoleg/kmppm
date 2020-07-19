import dev.whyoleg.kamp.build.*
import org.gradle.api.*

inline fun Project.kampBuild(crossinline block: KampBuildExtension.() -> Unit = {}) {
    apply { it.plugin(KampBuildPlugin::class.java) }
    extensions.configure(KampBuildExtension::class.java) { it.block() }
}
