import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*

plugins { `kotlin-dsl` }

buildscript {
    dependencies {
        val files = files("../library/build/classes/kotlin/main")
        if (files.singleFile.exists().also(::println)) {
            classpath(files)
        } else {
            repositories { maven { setUrl("https://dl.bintray.com/whyoleg/kamp") } }
            classpath("dev.whyoleg.kamp:kamp:0.1.8")
        }
    }
}

@UseExperimental(KampInternal::class)
kampBuildDev {
    with(BuiltInPlugins) {
        resolvePlugins(kotlinJvm, bintray, buildScan, updates)
    }
    dependencies(BuiltInDependencies.kotlin.plugin.gradle)
}
