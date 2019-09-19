import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*

plugins { `kotlin-dsl` }

buildscript {
    dependencies {
        val files = files("../library/build/classes/kotlin/main")
        if (files.singleFile.exists()) {
            classpath(files)
        } else {
            repositories { maven { setUrl("https://dl.bintray.com/whyoleg/kamp") } }
            classpath("dev.whyoleg.kamp:kamp:0.1.6")
        }
    }
}

@UseExperimental(KampInternal::class)
kampBuildDev(BuiltInVersions(kotlin = "1.3.41")) {
    require(BuiltInVersions().kamp == "0.1.6") { "BuiltIn kamp plugin version wrong" }
    with(BuiltInPlugins) {
        resolvePlugins(kotlinJvm, bintray, buildScan, updates)
    }
    dependencies(BuiltInDependencies.kotlin.plugin.gradle)
}