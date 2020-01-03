import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.dependency.*

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("dev.whyoleg.kamp:plugin:0.2-local")
    }
}

modules {
    "plugin"()
}

resolvePlugins(
    KotlinPlugins.stable.kotlinJvm,
    BuiltInPlugins.stable.kamp
)
