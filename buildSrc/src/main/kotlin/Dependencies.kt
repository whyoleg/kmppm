import dev.whyoleg.kamp.feature.kotlin.*
import dev.whyoleg.kamp.feature.shadow.*
import dev.whyoleg.kamp.feature.updates.*

object Dependencies {
    val kotlin = Kotlin.dependencies(KotlinVersion.CURRENT.toString())
    val updates = Updates.dependency(KampVersions.updates)
    val shadow = Shadow.dependency(KampVersions.shadow)
}
