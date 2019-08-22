plugins {
    id("com.github.ben-manes.versions")
    `build-scan`
    id("net.nemerosa.versioning")
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
