buildCache {
    local(DirectoryBuildCache::class) {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        kotlin("jvm") version "1.3.41"
    }
}

enableFeaturePreview("GRADLE_METADATA")
