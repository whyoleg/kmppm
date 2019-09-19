kampJvm {
    with(Plugins) {
        plugins(mavenPublish, bintray, javaPlugin)
    }
    source {
        main {
            with(Plugins) {
                compileOnly(kotlinJvm, shadow, updates, docker, detekt, bintray, buildScan)
            }
        }
    }

    publishing {
        bintray(publication) {
            repo = "kamp"
            override = false
            val commitPostfix = System.getenv("GITHUB_SHA")?.let { "-$it" } ?: ""
            version = "${configuration.version}$commitPostfix"
        }
    }
}