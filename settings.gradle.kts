buildCache {
    local(DirectoryBuildCache::class) {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

pluginManagement {
    repositories {
        jcenter()
        google()
        mavenCentral()
        maven { setUrl("https://plugins.gradle.org/m2/") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }

    resolutionStrategy.eachPlugin {
        if (requested.id.id == "org.jetbrains.kotlin.jvm") useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.40-eap-105")
    }
}
enableFeaturePreview("GRADLE_METADATA")
