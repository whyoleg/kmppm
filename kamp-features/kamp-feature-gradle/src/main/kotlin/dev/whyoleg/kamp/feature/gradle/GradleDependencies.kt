package dev.whyoleg.kamp.feature.gradle

import dev.whyoleg.kamp.dependency.*

class GradleDependencies(versions: GradleVersions) {
    val buildScan = dependency("com.gradle", "build-scan-plugin", versions.buildScan, KampPlatform.jvm)
}
