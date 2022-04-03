package dev.whyoleg.tdlib.kotlin

import kotlinx.cinterop.*
import tdlib.*
import kotlin.test.*

class TdlibTest {
    @Test
    fun test() {
        // disable TDLib logging
        td_execute("{\"@type\":\"setLogVerbosityLevel\", \"new_verbosity_level\":0}");

        val clientId = td_create_client_id();
        td_send(clientId, "{\"@type\":\"getOption\", \"name\":\"version\"}");

        while (true) {
            val result = td_receive(10.0)
            if (result != null) {
                println(result.toKString())
                break
            }
        }
    }

}
