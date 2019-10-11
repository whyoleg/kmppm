@file:Suppress("FunctionName", "unused")

import dev.whyoleg.kamp.module.*

object Modules : RootModule() {
    val library by named()

    object Dependencies : Module by named("dependencies") {
        val stable by named()
        val latest by named()
    }
}
