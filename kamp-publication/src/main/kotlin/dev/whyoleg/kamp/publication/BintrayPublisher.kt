package dev.whyoleg.kamp.publication

import org.gradle.api.artifacts.dsl.*
import java.net.*
import java.util.*

data class BintrayPublisher(
    val user: String,
    val repo: String,
    val name: String,
    val bintrayUser: String? = null,
    val bintrayApiKey: String? = null
) {
    private val bUser get() = bintrayUser ?: System.getenv("BINTRAY_USER")
    private val bApiKey get() = bintrayApiKey ?: System.getenv("BINTRAY_API_KEY")

    fun provider(publish: Boolean = false, override: Boolean = false): RepositoryHandler.() -> Unit = {
        maven {
            it.setUrl("https://api.bintray.com/maven/$user/$repo/$name/;publish=${if (publish) 1 else 0};override=${if (override) 1 else 0}")
            it.credentials { p ->
                p.username = bUser
                p.password = bApiKey
            }
        }
    }

    fun publish(version: String) {
        val responseCode =
            (URL("https://api.bintray.com/content/$user/$repo/$name/$version/publish").openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Authorization", "Basic " + Base64.getEncoder().encodeToString("$bUser:$bApiKey".toByteArray()))
            }.responseCode
        require(responseCode == 200) {
            "Artifact isn't published. StatusCode: $responseCode"
        }
    }
}
