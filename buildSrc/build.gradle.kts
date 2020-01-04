import dev.whyoleg.kamp.modules.*

plugins { `kotlin-dsl` }

buildscript {
    repositories { mavenLocal() }
    dependencies { classpath("dev.whyoleg.kamp:plugin:0.2-local") }
}

repositories {
    mavenCentral()
}

kotlin.target.dependenciesMain {
    implementation(BuiltInDependencies.Stable.kamp)
}
