package dev.whyoleg.td.json.prebuilt

internal actual fun loadLibrary(name: String) {
    println("LOAD_ANDROID: $name")
    System.loadLibrary(name)
}
