import org.jetbrains.kotlin.gradle.tasks.*
import java.util.*

plugins {
    id("com.github.ben-manes.versions") version "0.22.0"
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
    `build-scan`
    id("com.jfrog.bintray") version "1.8.4"
    id("net.nemerosa.versioning") version "2.8.2"
}

repositories {
    jcenter()
    google()
    mavenCentral()
    maven { setUrl("https://plugins.gradle.org/m2/") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
}

dependencies {
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.41")
    compileOnly("com.github.jengelman.gradle.plugins:shadow:5.1.0")
    compileOnly("com.github.ben-manes:gradle-versions-plugin:0.22.0")
    compileOnly("gradle.plugin.com.google.cloud.tools:jib-gradle-plugin:1.5.0")
    compileOnly("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.0.0")
}

tasks.withType<KotlinCompile> {
    val jdkVersion = "1.8"
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
    kotlinOptions.jvmTarget = jdkVersion
    kotlinOptions {
        apiVersion = "1.3"
        languageVersion = "1.3"
        freeCompilerArgs = listOf("-progressive", "-XXLanguage:+InlineClasses", "-XXLanguage:+NewInference")
    }
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"

    publishAlways()
}

group = "dev.whyoleg.kamp"
version = "0.1.0-${versioning.info.build}"

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(JavaPlugin.CLASSES_TASK_NAME)
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

publishing {
    publications {
        create<MavenPublication>("bintray") {
            groupId = "dev.whyoleg.kamp"
            artifactId = "kamp"
            // version is gotten from an external plugin
            version = "0.1.0-${versioning.info.build}"
            // This is the main artifact
            from(components["java"])
            // And sources
            artifact(sourcesJar)

            pom.withXml {
                asNode().apply {
                    appendNode("description", "Gradle plugin for kotlin MPP")
                    appendNode("name", rootProject.name)
                    appendNode("url", "https://github.com/whyoleg/kamp")
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", "The Apache Software License, Version 2.0")
                        appendNode("url", "http://www.apache.org/licenses/LICENSE-2.0.txt")
                        appendNode("distribution", "repo")
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", "whyoleg")
                        appendNode("name", "Oleg")
                        appendNode("email", "whyoleg@gmail.com")
                    }
                    appendNode("scm").apply {
                        appendNode("url", "git@github.com:whyoleg/kamp.git")
                        appendNode("connection", "scm:git:git@github.com:whyoleg/kamp.git")
                    }
                }
            }
        }
    }
}

bintray {
    // Getting bintray user and key from properties file or command line
    user = System.getenv("bintray_user")
    key = System.getenv("bintray_key")

    // Automatic publication enabled
    publish = true

    // Set maven publication onto bintray plugin
    setPublications("bintray")

    // Configure package
    pkg.apply {
        repo = "kamp"
        name = "kamp"
        setLicenses("Apache-2.0")
        setLabels("Kotlin", "MPP", "Plugin")
        vcsUrl = "git@github.com:whyoleg/kamp.git"
        websiteUrl = "https://github.com/whyoleg/kamp"
        githubRepo = "https://github.com/whyoleg/kamp"
        githubReleaseNotesFile = "https://github.com/whyoleg/kamp/README.md"

        // Configure version
        version.apply {
            name = "0.1.0-${versioning.info.build}"
            desc = "Gradle plugin for kotlin MPP"
            released = Date().toString()
//            vcsTag = project.versioning.info.tag
        }
    }
}

tasks.getByName("bintrayUpload") {
    dependsOn("build")
    dependsOn("sourcesJar")
    dependsOn("generatePomFileForBintrayPublication")
}