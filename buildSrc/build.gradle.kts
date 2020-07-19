buildscript {
    repositories {
        maven("https://dl.bintray.com/whyoleg/kamp")
    }
    dependencies {
        classpath("dev.whyoleg.kamp:kamp-build:0.3.0.beta.1")
    }
}

plugins {
    `kotlin-dsl`
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}

repositories {
    maven("https://dl.bintray.com/whyoleg/kamp")
    mavenCentral()
    gradlePluginPortal()
}

kampBuild {
    publication = true
    features {
        kotlin = true
        gradle = true
        shadow = true
        updates = true
    }
}
