package dev.whyoleg.td.json

import dev.whyoleg.td.json.internal.tdlib.*
import kotlinx.cinterop.*

public actual object TdJson {
    @Suppress("VARIABLE_IN_SINGLETON_WITHOUT_THREAD_LOCAL")
    private lateinit var native: TdJsonNative //TODO: or simple var?
    public actual fun createClientId(): Int = native.createClientId()
    public actual fun execute(request: String): String? = native.execute(request)?.toKString()
    public actual fun send(clientId: Int, request: String): Unit = native.send(clientId, request)
    public actual fun receive(timeout: Double): String? = native.receive(timeout)?.toKString()

    @PublishedApi
    internal fun init(native: TdJsonNative) {
        this.native = native
    }
}

@PublishedApi //implemented in other module
internal interface TdJsonNative {
    fun createClientId(): Int
    fun execute(request: String): CPointer<ByteVar>?
    fun send(clientId: Int, request: String)
    fun receive(timeout: Double): CPointer<ByteVar>?
}

@Suppress("DEPRECATION")
@OptIn(ExperimentalStdlibApi::class)
@EagerInitialization
private val initHook = TdJson.init(TdJsonImpl)


object TdJsonImpl : TdJsonNative {
    override fun createClientId(): Int = td_create_client_id()
    override fun execute(request: String): CPointer<ByteVar>? = td_execute(request)
    override fun send(clientId: Int, request: String): Unit = td_send(clientId, request)
    override fun receive(timeout: Double): CPointer<ByteVar>? = td_receive(timeout)
}
