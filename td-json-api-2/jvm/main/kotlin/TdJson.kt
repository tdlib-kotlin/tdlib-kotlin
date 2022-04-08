package dev.whyoleg.td.json

import java.io.*

public actual object TdJson {
    init {
        val os = System.getProperty("os.name").lowercase()
        when {
            os.contains("android", ignoreCase = false) -> Runtime.getRuntime().loadLibrary("tdjsonjni")
            os.contains("linux", ignoreCase = false)   -> Runtime.getRuntime().loadLibrary("libtdjsonjni", "so")
            else                                       -> TODO("Unsupported OS: $os")
        }
    }

    public actual external fun createClientId(): Int
    public actual external fun execute(request: String): String?
    public actual external fun send(clientId: Int, request: String)
    public actual external fun receive(timeout: Double): String?
}

@Suppress("UnsafeDynamicallyLoadedCode")
private fun Runtime.loadLibrary(name: String, extension: String) {
    val temp = tempFile(name, extension)

    val libraryStream =
        TdJson::class.java.classLoader!!
            .getResourceAsStream("lib/$name.$extension")
            ?: error("No library '$name.$extension' found in jar")

    libraryStream.use {
        temp.outputStream().use {
            libraryStream.copyTo(it)
        }
    }

    load(temp.canonicalPath)
}

private fun tempFile(name: String, extension: String): File {
    val tempFile = File.createTempFile(name, extension)
    tempFile.deleteOnExit()
    return tempFile
}
