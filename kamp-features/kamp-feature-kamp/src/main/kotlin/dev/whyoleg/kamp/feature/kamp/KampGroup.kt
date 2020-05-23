package dev.whyoleg.kamp.feature.kamp

import dev.whyoleg.kamp.dependency.*

object KampGroup : GroupWithVersionPlatforms by group("dev.whyoleg.kamp").version(Kamp.version).platforms(KampPlatform.jvm)
