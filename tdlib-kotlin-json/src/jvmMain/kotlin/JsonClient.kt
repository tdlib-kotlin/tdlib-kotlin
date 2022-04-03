package dev.whyoleg.tdlib.kotlin

object JsonClient {
    init {
        try {
            System.loadLibrary("tdlib-kotlin-json")
        } catch (e: UnsatisfiedLinkError) {
            e.printStackTrace()
        }
    }

    external fun create(): Long
    external fun send(client: Long, request: String?)
    external fun receive(client: Long, timeout: Double): String?
    external fun execute(client: Long, request: String?): String?
    external fun destroy(client: Long)
}
