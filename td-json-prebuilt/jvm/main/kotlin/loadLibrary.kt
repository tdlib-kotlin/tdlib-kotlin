package dev.whyoleg.td.json.prebuilt

import dev.whyoleg.td.json.*
import java.io.*

@Suppress("UnsafeDynamicallyLoadedCode")
internal actual fun loadLibrary(name: String) {
    println("LOAD_JVM: $name")
    val nativeName = System.mapLibraryName(name)
    val temp = nativeName.split(".").let { (n, e) -> File.createTempFile(n, e) }
    temp.deleteOnExit()

    val libraryStream =
        TdJson::class.java.classLoader
            ?.getResourceAsStream("lib/$nativeName")
            ?: error("No library '$nativeName' found in jar")

    libraryStream.use {
        temp.outputStream().use {
            libraryStream.copyTo(it)
        }
    }

    System.load(temp.canonicalPath)
}
