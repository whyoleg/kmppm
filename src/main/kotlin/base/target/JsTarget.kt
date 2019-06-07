package dev.whyoleg.kamp.base.target

data class JsTarget(override val name: String = "js") : PlatformTarget(name, { js(it.name) })