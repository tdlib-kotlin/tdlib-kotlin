package dev.whyoleg.td.json

public interface TdJson {
    public fun createClientId(): Int
    public fun execute(request: String): String?
    public fun send(clientId: Int, request: String)
    public fun receive(timeout: Double): String?

    public companion object : TdJson by TdJsonLoader.get() //TODO make lazy field
}

