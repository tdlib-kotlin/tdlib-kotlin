package dev.whyoleg.td.json.prebuilt

import dev.whyoleg.td.json.*
import dev.whyoleg.td.json.jni.*

public class PrebuiltJniTdJsonServiceLoader : TdJsonServiceLoader {
    override val instance: TdJson by lazy {
        loadLibrary("tdjson")
        loadLibrary("tdjsonjni")
        JniTdJson
    }
}

internal expect fun loadLibrary(name: String)
