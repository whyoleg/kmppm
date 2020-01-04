import dev.whyoleg.kamp.modules.*

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
    "lib"()
}

resolvePlugins(
    KotlinPlugins.Stable.kotlinJvm,
    BuiltInPlugins.Stable.kamp
)
