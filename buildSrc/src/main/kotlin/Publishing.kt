import dev.whyoleg.kamp.publication.*
import org.gradle.api.*
import org.gradle.api.publish.maven.*

@PublishedApi
internal val publication = Publication(
    name = "kamp",
    description = "Gradle plugin for kotlin",
    licenses = listOf(License.apache2),
    developers = listOf(Developer("whyoleg", "Oleg", "whyoleg@gmail.com")),
    url = "https://github.com/whyoleg/kamp",
    vcsUrl = "git@github.com:whyoleg/kamp.git"
)

val publisher = BintrayPublisher("whyoleg", "kamp", "kamp")

inline fun Project.configurePublication(
    artifactId: String,
    crossinline block: MavenPublication.() -> Unit = {}
) {
    jvmPublication(publication, publisher) {
        this.artifactId = "kamp-$artifactId"
        apply(block)
    }
}
