plugins {
    ids(Plugins.kotlin)
}

configureFeature("updates") {
    target.dependenciesMain {
        compileOnly(Dependencies.updates)
    }
}
