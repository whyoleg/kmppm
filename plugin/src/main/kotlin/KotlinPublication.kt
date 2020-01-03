import dev.whyoleg.kamp.publication.*
import org.gradle.api.*
import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.publication(publication: Publication) {
    targets.all { target -> target.mavenPublication(Action { it.pom(publication) }) }
}
