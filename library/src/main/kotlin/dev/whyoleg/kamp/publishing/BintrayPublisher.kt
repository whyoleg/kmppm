package dev.whyoleg.kamp.publishing

import com.jfrog.bintray.gradle.*
import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*
import dev.whyoleg.kamp.plugin.Plugin
import org.gradle.api.*
import java.util.*

class BintrayPublisher internal constructor(
    private val publication: Publication,
    private val configuration: ProjectConfiguration
) : Publisher {
    override val plugins: Set<Plugin> = with(BuiltInPlugins) { setOf(bintray, mavenPublish, javaPlugin) }

    var user: String? = null
    var key: String? = null
    var repo: String? = null
    var autoPublish: Boolean = true
    var override: Boolean = false
    var version: String? = null

    override fun Project.configure() {

        tasks.getByName("bintrayUpload") {
            it.dependsOn("build")
            it.dependsOn("sourcesJar")
            it.dependsOn("generatePomFileFor${publication.name.capitalize()}Publication")
        }

        extensions.configure<BintrayExtension>("bintray") {
            it.user = user ?: System.getenv("bintray_user")
            it.key = key ?: System.getenv("bintray_key")
            it.publish = autoPublish
            it.override = override
            it.setPublications(publication.name)
            it.pkg.apply {
                repo = this@BintrayPublisher.repo
                name = publication.name
                setLicenses(*publication.licenses.map(License::name).toTypedArray())
                setLabels(*publication.labels.toTypedArray())
                vcsUrl = publication.vcsUrl
                websiteUrl = publication.websiteUrl
                githubRepo = publication.githubUrl

                version.apply {
                    name = this@BintrayPublisher.version ?: configuration.version(this@configure)
                    desc = publication.description
                    released = Date().toString()
                }
            }
        }
    }
}