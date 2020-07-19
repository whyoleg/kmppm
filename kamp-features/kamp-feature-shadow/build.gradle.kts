plugins {
    ids(Plugins.kotlin)
}

configureFeature("shadow") {
    target.kampSourceSetMain.dependencies {
        compileOnly(Dependencies.shadow)
    }
}
