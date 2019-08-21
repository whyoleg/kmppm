plugins {
    id("com.github.ben-manes.versions")
    `build-scan`
    id("net.nemerosa.versioning") version "2.8.2"
}

repositories {
    jcenter()
    google()
    mavenCentral()
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

group = "dev.whyoleg.kamp"
version = "0.1.0-${versioning.info.build}"
