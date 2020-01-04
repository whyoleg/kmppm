import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.publication.*
import dev.whyoleg.kamp.publication.Publication
import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.api.publish.*
import org.gradle.api.publish.maven.*
import org.gradle.api.tasks.*
import org.gradle.jvm.tasks.*
import org.jetbrains.kotlin.gradle.dsl.*

fun KotlinMultiplatformExtension.publication(publication: Publication) {
    targets.all { target -> target.mavenPublication(Action { it.pom(publication) }) }
}

fun MavenPublication.pom(publication: Publication) {
    pom.withXml { it.configurePublication(publication) }
}

@Suppress("UnstableApiUsage")
inline fun Project.publishingProvider(crossinline provider: RepositoryProvider) {
    extensions.configure<PublishingExtension>("publishing") {
        it.repositories {
            provider(it)
        }
    }
}

@Suppress("UnstableApiUsage")
inline fun Project.jvmPublication(
    publication: Publication,
    crossinline provider: RepositoryProvider,
    crossinline configure: MavenPublication.() -> Unit
) {
    publishingProvider(provider)
    val sourcesJar = tasks.create("sourcesJar", Jar::class.java) {
        it.dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        it.archiveClassifier.set("sources")
        it.from((extensions.getByName("sourceSets") as SourceSetContainer).getByName("main").allSource)
    }
    extensions.configure<PublishingExtension>("publishing") { ext ->
        ext.publications { pub ->
            pub.create(publication.name, MavenPublication::class.java) { mavenPub ->
                mavenPub.apply {
                    groupId = this@jvmPublication.group.toString()
                    version = this@jvmPublication.version.toString()
                    artifactId = this@jvmPublication.name

                    from(components.getByName("java"))
                    artifact(sourcesJar)

                    pom(publication)
                    apply(configure)
                }
            }
        }
    }
}

fun Project.createBintrayPublishTask(publisher: BintrayPublisher, version: String? = null) {
    tasks.register("publish${publisher.name.toLowerCase().capitalize()}OnBintray") {
        it.group = "bintray"
        it.doLast {
            publisher.publish(version ?: this.version.toString())
        }
    }
}
