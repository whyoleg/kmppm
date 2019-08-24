kampJvm {
    with(Plugins) {
        plugins(versioning, mavenPublish, bintray, javaPlugin)
    }
    source {
        main {
            with(Plugins) {
                compileOnly(kotlinJvm, shadow, updates, docker, detekt, versioning, bintray, buildScan)
            }
        }
    }

    publishing {
        bintray(publication) {
            repo = "kamp"
            override = false
        }
    }
}