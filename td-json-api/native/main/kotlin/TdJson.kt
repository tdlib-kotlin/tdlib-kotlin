package dev.whyoleg.td.json

import dev.whyoleg.td.json.internal.tdlib.*
import kotlinx.cinterop.*

public actual sealed interface TdJson {
    public actual fun createClientId(): Int
    public actual fun execute(request: String): String?
    public actual fun send(clientId: Int, request: String)
    public actual fun receive(timeout: Double): String?
}

public actual fun TdJson(): TdJson = TdJsonImpl

private object TdJsonImpl : TdJson {
    override fun createClientId(): Int = td_create_client_id()
    override fun execute(request: String): String? = td_execute(request)?.toKString()
    override fun send(clientId: Int, request: String): Unit = td_send(clientId, request)
    override fun receive(timeout: Double): String? = td_receive(timeout)?.toKString()
}
