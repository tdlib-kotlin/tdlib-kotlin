package dev.whyoleg.td.json.prebuilt

import dev.whyoleg.td.json.*
import dev.whyoleg.td.json.prebuilt.internal.tdlib.*
import kotlinx.cinterop.*

@Suppress("DEPRECATION")
@OptIn(ExperimentalStdlibApi::class)
@EagerInitialization
private val initHook = TdJsonLoader.load(NativeTdJson)

private object NativeTdJson : TdJson {
    override fun createClientId(): Int = td_create_client_id()
    override fun execute(request: String): String? = td_execute(request)?.toKString()
    override fun send(clientId: Int, request: String): Unit = td_send(clientId, request)
    override fun receive(timeout: Double): String? = td_receive(timeout)?.toKString()
}
