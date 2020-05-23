plugins {
    id("com.github.ben-manes.versions")
}

allprojects {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}


//tasks.withType<DependencyUpdatesTask> {
//    outputFormatter = "json"
//    closureOf<com.github.benmanes.gradle.versions.reporter.result.Result> {
////        outdated.dependencies.forEach {
////            println(it.name)
////            println(it.group)
////            println(it.version)
////            println(it.available.milestone)
////            println(it.available.integration)
////            println(it.available.release)
////            println()
////        }
////    }
//}
//comment/override
//type: milestone/integration/release
//mapping (group, name) -> key
