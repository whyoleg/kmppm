package dev.whyoleg.kamp.publishing

data class Publication(
    val name: String,
    val description: String,
    val licenses: List<License>,
    val developers: List<Developer>,
    val labels: List<String>,
    val scmConnections: String,
    val vcsUrl: String,
    val websiteUrl: String,
    val githubUrl: String
)

data class License(
    val name: String,
    val url: String,
    val disrtribution: String
)

data class Developer(
    val id: String,
    val name: String,
    val email: String
)