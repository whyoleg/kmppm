//import dev.whyoleg.kamp.dependency.*
//import dev.whyoleg.kamp.dependency.builder.*
//import dev.whyoleg.kamp.modules.*

//plugins { `kotlin-dsl` }

//buildscript {
//    val kampVersion = if (properties["dev.whyoleg.bootstrap"].toString().toBoolean()) {
//        repositories { mavenLocal() }
//        "bootstrap"
//    } else {
//        repositories { maven("https://dl.bintray.com/whyoleg/kamp") }
//        "0.2.1-pre-5"
//    }
//    dependencies { classpath("dev.whyoleg.kamp:kamp:$kampVersion") }
//}

//kotlin.target.dependenciesMain {
//    val kampVersion = if (properties["dev.whyoleg.bootstrap"].toString().toBoolean()) "bootstrap" else "0.2.1-pre-5"
//    implementation(BuiltInDependencies.Stable.kamp.version(kampVersion, RepositoryProviders.mavenLocal))
//}
buildscript {
    repositories {
        mavenLocal()
    }
    dependencies {
        classpath("dev.whyoleg.kamp:kamp-build-plugin:0.3.0-1")
    }
}

plugins {
    `kotlin-dsl`
//    id("dev.whyoleg.kamp.build")
}

kotlinDslPluginOptions { experimentalWarning.set(false) }

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

kamp {
    publication = true
    features {
        kotlin = true
        gradle = true
        shadow = true
        updates = true
    }
}
