package dev.whyoleg.kamp.publication

import org.gradle.api.publish.maven.*

data class License(
    val name: String,
    val url: String,
    val distribution: String? = null,
    val comments: String? = null
) {
    companion object {
        val apache2: License = License("Apache-2.0", "http://www.apache.org/licenses/LICENSE-2.0.txt", "repo")
    }
}

internal fun MavenPom.licenses(licenses: List<License>) {
    if (licenses.isEmpty()) return

    licenses {
        licenses.forEach { license ->
            it.license(license::configure)
        }
    }
}

private fun License.configure(pomLicense: MavenPomLicense) {
    pomLicense.name.set(name)
    pomLicense.url.set(url)
    pomLicense.distribution.set(distribution)
    comments?.let(pomLicense.comments::set)
}
