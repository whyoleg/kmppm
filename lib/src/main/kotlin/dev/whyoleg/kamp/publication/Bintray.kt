package dev.whyoleg.kamp.publication

import dev.whyoleg.kamp.dependency.*
import org.apache.http.client.methods.*
import org.apache.http.impl.client.*
import java.util.*

data class BintrayPublisher(
    val user: String,
    val repo: String,
    val name: String,
    val bintrayUser: String? = null,
    val bintrayApiKey: String? = null
) {

    fun provider(publish: Boolean = false, override: Boolean = false): RepositoryProvider =
        RepositoryProviders.bintrayPublish(user, repo, name, publish, override, bintrayUser, bintrayApiKey)

    fun publish(version: String) {
        val bUser = bintrayUser ?: System.getenv("BINTRAY_USER")
        val bKey = bintrayApiKey ?: System.getenv("BINTRAY_API_KEY")

        val client = HttpClientBuilder.create().build()
        val request = HttpPost("https://api.bintray.com/content/$user/$repo/$name/$version/publish").apply {
            addHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString("$bUser:$bKey".toByteArray()))
        }
        val status = client.execute(request).statusLine
        require(status.statusCode == 200) { "Artifact isn't published. StatusCode: ${status.statusCode}" }
    }
}
