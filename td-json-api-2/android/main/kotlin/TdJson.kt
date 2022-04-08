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
        //TODO loading per platform
        // also suppress exceptions
        // android vs jvm loading
        println("TRY: tdjson")
        runCatching {
            System.loadLibrary("tdjson")
        }.onFailure {
            println("tslib: failed to load native library")
            it.printStackTrace()
        }
        println("TRY: tdjsonjni")
        runCatching {
            System.loadLibrary("tdjsonapijni")
        }.onFailure {
            println("tdjsonjni: failed to load native library")
            it.printStackTrace()
        }
        println("FIN")
    }

    external override fun createClientId(): Int
    external override fun execute(request: String): String?
    external override fun send(clientId: Int, request: String)
    external override fun receive(timeout: Double): String?
}
