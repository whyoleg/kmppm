plugins {
    ids(Plugins.kotlin)
}

configureFeature("shadow") {
    target.dependenciesMain {
        compileOnly(Dependencies.shadow)
    }
}
