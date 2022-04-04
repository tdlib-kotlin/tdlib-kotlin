package dev.whyoleg.td.json

public actual sealed interface TdJson {
    public actual fun createClientId(): Int
    public actual fun execute(request: String): String?
    public actual fun send(clientId: Int, request: String)
    public actual fun receive(timeout: Double): String?
}

public actual fun TdJson(): TdJson {
    return TdJsonJni
}

private object TdJsonJni : TdJson {
    init {
        //TODO loading

        //TODO: load from resources
        runCatching {
            System.loadLibrary("tdjson")
        }.onFailure {
            println("tslib: failed to load native library")
            it.printStackTrace()
        }

        runCatching {
            System.loadLibrary("td-json-api")
        }.onFailure {
            println("td-json-jvm: failed to load native library")
            it.printStackTrace()
        }
    }

    external override fun createClientId(): Int
    external override fun execute(request: String): String?
    external override fun send(clientId: Int, request: String)
    external override fun receive(timeout: Double): String?
}
