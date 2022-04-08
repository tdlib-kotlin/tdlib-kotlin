package dev.whyoleg.td.json.jni

import dev.whyoleg.td.json.*

public object JniTdJson : TdJson {
    external override fun createClientId(): Int
    external override fun execute(request: String): String?
    external override fun send(clientId: Int, request: String)
    external override fun receive(timeout: Double): String?
}
