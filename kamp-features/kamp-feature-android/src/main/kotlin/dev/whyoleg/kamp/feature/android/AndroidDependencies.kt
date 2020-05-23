package dev.whyoleg.kamp.feature.android

import dev.whyoleg.kamp.dependency.*

class AndroidDependencies(versions: AndroidVersions) {
    val plugin = dependency("com.android.tools.build", "gradle", versions.plugin, KampPlatform.jvm)
}
