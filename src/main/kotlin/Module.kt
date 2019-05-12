@file:Suppress("FunctionName", "unused")

package dev.whyoleg.kmppm

object Module {

    fun <T> Project(block: Project.() -> T): T = block(Project)

    object Project {
        const val data = ":data"
        const val auth = ":auth"
        const val crypt = ":crypt"
        const val serialization = ":serialization"
        const val transport = ":transport"

        val all = listOf(data, auth, serialization, transport, crypt)
    }

    fun <T> Server(block: Server.() -> T): T = block(Server)

    object Server {
        const val api = ":server:api"
        const val content = ":server:content"

        val all = listOf(api, content)

        fun <T> Resolver(block: Resolver.() -> T): T = block(Resolver)

        object Resolver {
            const val app = ":server:resolver:app"
            const val database = ":server:resolver:database"

            val all = listOf(app, database)
        }

        fun <T> Social(block: Social.() -> T): T = block(Social)

        object Social {

            fun <T> Telegram(block: Telegram.() -> T): T = block(Telegram)

            object Telegram {
                const val api = ":server:social:telegram-api"
                const val app = ":server:social:telegram-app"

                private val apiPair = api to "server/social/telegram/api"
                private val appPair = app to "server/social/telegram/app"

                val all = listOf(api, app)

                val allPairs = listOf(apiPair, appPair)
            }
        }
    }

    fun <T> Client(block: Client.() -> T): T = block(Client)

    object Client {
        const val arch = ":client:arch"
        const val app = ":client:app"
        const val network = ":client:network"
        const val database = ":client:database"
        const val query = ":client:query"

        val all = listOf(arch, app, network, database, query)
    }

    val all = Project.all + Client.all + Server.all + Server.Resolver.all

    val allPairs = Module.Server.Social.Telegram.allPairs
}