import dev.whyoleg.kamp.publication.*
import dev.whyoleg.kamp.publication.Publication
import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.api.publish.*
import org.gradle.api.publish.maven.*
import org.gradle.api.tasks.*
import org.gradle.jvm.tasks.*

inline fun Project.jvmPublication(
    publication: Publication,
    publisher: BintrayPublisher,
    crossinline block: MavenPublication.() -> Unit = {}
) {
    val sourcesJar = tasks.register("sourcesJar", Jar::class.java) {
        it.dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        it.archiveClassifier.set("sources")
        it.from((extensions.getByName("sourceSets") as SourceSetContainer).getByName("main").allSource)
    }

    extensions.configure<PublishingExtension>("publishing") { ext ->
        publisher.provide(ext.repositories)
        ext.publications { pub ->
            pub.register(publication.name, MavenPublication::class.java) { mavenPub ->
                mavenPub.apply {
                    from(components.getByName("java"))
                    artifact(sourcesJar.get())
                    pom.configure(publication)
                    apply(block)
                }
            }
        }
    }

    tasks.register("publish${publication.name.capitalize()}OnBintray") {
        it.group = "bintray"
        it.doLast {
            publisher.publish {
                version = this@jvmPublication.version.toString()
            }
        }
    }
}

inline fun <T> Project.mppPublication(
    publication: Publication,
    publisher: Publisher<T, *>,
    crossinline block: T.() -> Unit = {}
): Unit = extensions.configure(PublishingExtension::class.java) {
    publisher.provide(it.repositories, block)
    it.publications.all { pub ->
        (pub as? MavenPublication)?.pom?.configure(publication)
    }
}

inline fun Project.forAllMavenPublications(crossinline block: MavenPublication.(container: PublicationContainer) -> Unit = {}) {
    extensions.configure(PublishingExtension::class.java) {
        it.publications.all { pub ->
            (pub as? MavenPublication)?.block(it.publications)
        }
    }
}

inline fun <T> Project.createPublishTask(
    publication: Publication,
    publisher: Publisher<*, T>,
    crossinline block: T.() -> Unit = {}
) {
    tasks.register("publish${publication.name.capitalize()}") {
        it.group = "publishing"
        it.doLast { publisher.publish(block) }
    }
}
