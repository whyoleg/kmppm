import dev.whyoleg.kamp.plugin.*

configuredLibrary {
    with(BuiltInPlugins) {
        plugins(javaPlugin)
        source { main { compileOnly(kotlinJvm, shadow, updates, docker, detekt, bintray, buildScan) } }
    }
    publishing {
        bintray(publication) { repo = "kamp" }
    }
}
