import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.publication.*

val publication = Publication(
    name = "kamp",
    description = "Gradle plugin for kotlin MPP",
    licenses = listOf(License.apache2),
    developers = listOf(Developer("whyoleg", "Oleg", "whyoleg@gmail.com")),
    labels = listOf("Kotlin", "MPP"),
    url = "https://github.com/whyoleg/kamp",
    vcsUrl = "git@github.com:whyoleg/kamp.git"
)

val publisher = BintrayPublisher("whyoleg", "kamp", "kamp")
