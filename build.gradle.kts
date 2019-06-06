import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugin.updates) version Version.updates
    kotlin("jvm") version Version.kotlin
    `java-gradle-plugin`
    `maven-publish`
}

group = "dev.whyoleg.kamp"
version = "0.1.0"

repositories {
    jcenter()
    mavenCentral()
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
    maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
}

dependencies {
    compileOnly(Dependencies.kotlinGradlePlugin)
}

gradlePlugin {
    plugins {
        create("kamp") {
            id = "dev.whyoleg.kamp"
            implementationClass = "dev.whyoleg.kamp.KampPlugin"
        }
    }
}

tasks.withType<KotlinCompile> {
    sourceCompatibility = Version.jdk
    targetCompatibility = Version.jdk
    kotlinOptions.jvmTarget = Version.jdk
    kotlinOptions {
        apiVersion = Version.kotlinApi
        languageVersion = Version.kotlinLanguage
        freeCompilerArgs = Compiler.all()
    }
}
