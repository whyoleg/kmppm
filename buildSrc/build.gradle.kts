import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.dependency.*

plugins { `kotlin-dsl` }

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("dev.whyoleg.kamp:plugin:0.2-local")
    }
}
repositories {
    mavenLocal()
    mavenCentral()
}

resolvePlugins(
    KotlinPlugins.stable.kotlinJvm,
    BuiltInPlugins.stable.kamp
)
