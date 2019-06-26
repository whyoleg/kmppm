package dev.whyoleg.kamp.target.configuration

import org.jetbrains.kotlin.gradle.dsl.*

interface TargetOptions

data class JvmTargetOptions(
    var includeRuntime: Boolean = false,
    var javaParameters: Boolean = false,
    var jdkHome: String? = null,
    var jvmTarget: String = "1.6",
    var noJdk: Boolean = false,
    var noReflect: Boolean = true,
    var noStdlib: Boolean = true,
    var sourceCompatibility: String = "1.6",
    var targetCompatibility: String = "1.6"
) : TargetOptions {
    fun configure(kotlinOptions: KotlinJvmOptions) {
        with(kotlinOptions) {
            includeRuntime = this@JvmTargetOptions.includeRuntime
            javaParameters = this@JvmTargetOptions.javaParameters
            jdkHome = this@JvmTargetOptions.jdkHome
            jvmTarget = this@JvmTargetOptions.jvmTarget
            noJdk = this@JvmTargetOptions.noJdk
            noReflect = this@JvmTargetOptions.noReflect
            noStdlib = this@JvmTargetOptions.noStdlib
        }
    }
}

data class JsTargetOptions(
    var friendModulesDisabled: Boolean = false,
    var main: String = "call",
    var metaInfo: Boolean = true,
    var moduleKind: String = "plain",
    var noStdlib: Boolean = true,
    var outputFile: String? = null,
    var sourceMap: Boolean = false,
    var sourceMapEmbedSources: String? = null,
    var sourceMapPrefix: String? = null,
    var target: String = "v5",
    var typedArrays: Boolean = true
) : TargetOptions {
    fun configure(kotlinOptions: KotlinJsOptions) {
        with(kotlinOptions) {
            friendModulesDisabled = this@JsTargetOptions.friendModulesDisabled
            main = this@JsTargetOptions.main
            metaInfo = this@JsTargetOptions.metaInfo
            moduleKind = this@JsTargetOptions.moduleKind
            noStdlib = this@JsTargetOptions.noStdlib
            outputFile = this@JsTargetOptions.outputFile
            sourceMap = this@JsTargetOptions.sourceMap
            sourceMapEmbedSources = this@JsTargetOptions.sourceMapEmbedSources
            sourceMapPrefix = this@JsTargetOptions.sourceMapPrefix
            target = this@JsTargetOptions.target
        }
    }
}