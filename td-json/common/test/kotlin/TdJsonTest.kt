package dev.whyoleg.td.json

import kotlin.test.*

class TdJsonTest {
    @Test
    fun test() {
        val td = TdJson()

        // disable TDLib logging
        println(td.execute("""{"@type":"setLogVerbosityLevel", "new_verbosity_level":0}"""))

        val clientId = td.createClientId()
        td.send(clientId, """{"@type":"getOption", "name":"version"}""")

        var result: String?
        do {
            result = td.receive(10.0)
        } while (result == null)
        println(result)
    }
}
