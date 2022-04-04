package dev.whyoleg.tdlib.kotlin

object JsonClient {
    init {
        println(1)
        kotlin.runCatching {
            System.loadLibrary("tdjson")
        }.onFailure {
            println("tslib: failed to load native library")
            it.printStackTrace()
        }
        println(2)

        kotlin.runCatching {
            System.loadLibrary("tdlib-kotlin-json")
        }.onFailure {
            println("tdlib-kotlin-json: failed to load native library")
            it.printStackTrace()
        }
    }

    external fun create(): Int
    external fun send(client: Int, request: String?)
    external fun receive(timeout: Double): String?
    external fun execute(request: String?): String?
}
