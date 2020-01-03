buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("dev.whyoleg.kamp:plugin:0.2-local")
    }
}

modules {
    "plugin"()
}
