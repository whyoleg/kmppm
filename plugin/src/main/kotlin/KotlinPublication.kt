import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.publication.*
import dev.whyoleg.kamp.publication.Publication
import org.gradle.api.*
import org.gradle.api.publish.*
import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.publication(publication: Publication) {
    targets.all { target -> target.mavenPublication(Action { it.pom(publication) }) }
}

fun PublishingExtension.pub() {
    repositories {
        RepositoryProviders.bintrayPublish("", "", "")(it)
    }
}
