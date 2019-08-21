package dev.whyoleg.kamp.target

data class JsTarget(override val name: String = "js") : PlatformTarget({ js(name) })