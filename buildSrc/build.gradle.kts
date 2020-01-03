import dev.whyoleg.kamp.dependencies.*
import dev.whyoleg.kamp.dependency.*

plugins { `kotlin-dsl` }

buildscript {
    repositories { mavenLocal() }
    dependencies { classpath("dev.whyoleg.kamp:plugin:0.2-local") }
}

repositories {
    mavenCentral()
}

kotlin {
    target.dependenciesMain {
        implementation(BuiltInDependencies.stable.kamp)
    }
}
