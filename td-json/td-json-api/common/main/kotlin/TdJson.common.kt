package dev.whyoleg.td.json

//should be expect to make it possible to inherit in targets
public expect sealed interface TdJson {
    public fun createClientId(): Int
    public fun execute(request: String): String?
    public fun send(clientId: Int, request: String)
    public fun receive(timeout: Double): String?
}

public expect fun TdJson(): TdJson
