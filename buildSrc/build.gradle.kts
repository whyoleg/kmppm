import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.dependency.builder.*
import dev.whyoleg.kamp.modules.*

plugins { `kotlin-dsl` }

buildscript {
    repositories { mavenLocal() }
    val kampVersion = if (properties["dev.whyoleg.bootstrap"].toString().toBoolean()) "bootstrap" else "0.2.0"
    dependencies { classpath("dev.whyoleg.kamp:kamp:$kampVersion") }
}

kotlin.target.dependenciesMain {
    val kampVersion = if (properties["dev.whyoleg.bootstrap"].toString().toBoolean()) "bootstrap" else "0.2.0"
    implementation(BuiltInDependencies.Stable.kamp.version(kampVersion, RepositoryProviders.mavenLocal))
}
