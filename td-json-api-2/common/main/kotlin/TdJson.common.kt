package dev.whyoleg.td.json

public expect object TdJson {
    public fun createClientId(): Int
    public fun execute(request: String): String?
    public fun send(clientId: Int, request: String)
    public fun receive(timeout: Double): String?
}
