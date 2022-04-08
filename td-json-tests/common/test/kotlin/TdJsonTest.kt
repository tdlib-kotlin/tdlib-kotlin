package dev.whyoleg.td.json.tests

import dev.whyoleg.td.json.*
import kotlin.test.*

class TdJsonTest {
    @Test
    fun test() {
        // disable TDLib logging
        println(TdJson.execute("""{"@type":"setLogVerbosityLevel", "new_verbosity_level":0}"""))

        val clientId = TdJson.createClientId()
        TdJson.send(clientId, """{"@type":"getOption", "name":"version"}""")

        var result: String?
        do {
            result = TdJson.receive(10.0)
        } while (result == null)
        println(result)
    }
}
