import dev.whyoleg.kamp.publication.*
import dev.whyoleg.kamp.publication.Publication
import org.gradle.api.*
import org.gradle.api.artifacts.dsl.*
import org.gradle.api.plugins.*
import org.gradle.api.publish.*
import org.gradle.api.publish.maven.*
import org.gradle.api.tasks.*
import org.gradle.jvm.tasks.*
import org.jetbrains.kotlin.gradle.dsl.*

//fun KotlinMultiplatformExtension.publication(publication: Publication) {
//    targets.all { target -> target.mavenPublication(Action { it.pom.configure(publication) }) }
//}

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
        ext.repositories(publisher.provider())
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
        it.doLast { publisher.publish(version.toString()) }
    }
}
