package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*

abstract class KampBase(versions: BuiltInVersions) {
    val builtIn: BuiltIn = BuiltIn(versions)
}