package dev.whyoleg.tdlib.kotlin

import kotlin.test.*

class TdlibTest {
    @Test
    fun test() {
        val clientId = JsonClient.create();

        // disable TDLib logging
        JsonClient.execute(clientId, "{\"@type\":\"setLogVerbosityLevel\", \"new_verbosity_level\":0}");

        JsonClient.send(clientId, "{\"@type\":\"getOption\", \"name\":\"version\"}");

        while (true) {
            val result = JsonClient.receive(clientId, 10.0)
            if (result != null) {
                println(result)
                break
            }
        }
    }

}
