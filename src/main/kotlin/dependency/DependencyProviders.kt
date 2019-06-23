package dev.whyoleg.kamp.dependency

interface DependencyProviders {
    val google get() = Companion.google
    val mavenLocal get() = Companion.mavenLocal
    val mavenCentral get() = Companion.mavenCentral
    val jcenter get() = Companion.jcenter
    val gradlePluginPortal get() = Companion.gradlePluginPortal
    fun maven(url: String): DependencyProvider = Companion.maven(url)

    companion object {
        val google: DependencyProvider = { google() }
        val mavenLocal: DependencyProvider = { mavenLocal() }
        val mavenCentral: DependencyProvider = { mavenCentral() }
        val jcenter: DependencyProvider = { jcenter() }
        val gradlePluginPortal: DependencyProvider = { gradlePluginPortal() }

        fun maven(url: String): DependencyProvider = { maven { it.setUrl(url) } }
    }
}

interface MavenLocalProviderClassifier : DependencyProviderClassifier {
    override val provider: DependencyProvider get() = mavenLocal
}

interface MavenCentralProviderClassifier : DependencyProviderClassifier {
    override val provider: DependencyProvider get() = mavenCentral
}

interface JcenterProviderClassifier : DependencyProviderClassifier {
    override val provider: DependencyProvider get() = jcenter
}

interface GoogleProviderClassifier : DependencyProviderClassifier {
    override val provider: DependencyProvider get() = google
}

interface GradlePluginProviderClassifier : DependencyProviderClassifier {
    override val provider: DependencyProvider get() = gradlePluginPortal
}

interface MavenProviderClassifier : DependencyProviderClassifier {
    val url: String
    override val provider: DependencyProvider get() = maven(url)
}

interface KotlinxProviderClassifier : MavenProviderClassifier {
    override val url: String get() = "https://kotlin.bintray.com/kotlinx"
}

interface KotlinEapProviderClassifier : MavenProviderClassifier {
    override val url: String get() = "https://dl.bintray.com/kotlin/kotlin-eap"
}

interface KotlinDevProviderClassifier : MavenProviderClassifier {
    override val url: String get() = "https://dl.bintray.com/kotlin/kotlin-dev"
}

interface KtorProviderClassifier : MavenProviderClassifier {
    override val url: String get() = "https://dl.bintray.com/kotlin/ktor"
}

interface JitPackProviderClassifier : MavenProviderClassifier {
    override val url: String get() = "https://jitpack.io"
}
