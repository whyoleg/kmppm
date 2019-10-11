import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.dependency.*
import dev.whyoleg.kamp.plugin.*
import dev.whyoleg.kamp.version.*

plugins { `kotlin-dsl` }

buildscript {
    dependencies {
        val files = files("../library/build/classes/kotlin/main")
        if (files.singleFile.exists().also(::println)) {
            classpath(files)
        } else {
            repositories { maven { setUrl("https://dl.bintray.com/whyoleg/kamp") } }
            classpath("dev.whyoleg.kamp:kamp:0.1.10")
        }
    }
}

@UseExperimental(KampInternal::class)
kampBuildDev {
    registerVersions {
        default(BuiltInVersions.Stable)

        //versions for dependencies check
        "stable" use BuiltInVersions.Stable
        "latest" use BuiltInVersions.Latest
    }
    with(BuiltInPlugins) {
        resolvePlugins(kotlinJvm, bintray, buildScan, updates)
    }
    dependencies(BuiltInDependencies.kotlin.plugin.gradle)
}
