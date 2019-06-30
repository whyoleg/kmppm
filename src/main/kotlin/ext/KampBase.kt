package dev.whyoleg.kamp.ext

import dev.whyoleg.kamp.*
import dev.whyoleg.kamp.builtin.*

abstract class KampBase(versions: BuiltInVersions) {
    internal val builtIn: BuiltIn = BuiltIn(versions)
}