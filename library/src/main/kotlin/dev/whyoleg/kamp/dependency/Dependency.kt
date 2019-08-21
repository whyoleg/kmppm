package dev.whyoleg.kamp.dependency

import dev.whyoleg.kamp.target.Target

interface Dependency
interface TypedDependency<T : Target> : Dependency
interface UnTypedDependency : Dependency
