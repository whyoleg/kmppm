package dev.whyoleg.kamp.settings

private const val versionPrefix = "kamp.version."

@OptIn(ExperimentalStdlibApi::class)
internal fun Map<String, Any>.parseVersions(clsName: String): List<String> {
    val versions = filterKeys { it.startsWith(versionPrefix) }.map { (key, version) ->
        key.substringAfter(versionPrefix).split('.') to version as String
    }

    return buildList {
        add("/*Autogenerated with kamp, don't change*/")
        versions.parseTo(this, clsName, "")
    }
}

private fun List<Pair<List<String>, String>>.parseTo(result: MutableList<String>, name: String, indent: String) {
    val (single, multi) = partition { it.first.size == 1 }
    val singleVersions = single.map { it.first.first() to it.second }
    val mapVersions = multi.groupBy { it.first.first() }.mapValues { it.value.map { it.first.drop(1) to it.second } }

    val newIndent = "$indent    "
    result.apply {
        if (indent.isNotEmpty()) add("")
        add("${indent}object ${name.capitalize()} {")
        singleVersions.forEach { (key, version) ->
            add("""${newIndent}const val $key = "$version"""")
        }
        mapVersions.forEach { (key, other) ->
            other.parseTo(this, key, newIndent)
        }
        add("${indent}}")
    }
}

fun main() {
    val map = mapOf(
        "kamp.version.kotlin" to "123",
        "kamp.version.kotlinx.coroutines" to "1234",
        "kamp.version.kotlinx.serialization" to "12345",
        "kamp.version.android.plugin" to "123643",
        "kamp.version.detekt" to "12323"
    )

    map.parseVersions("KampVersions").forEach(::println)
}