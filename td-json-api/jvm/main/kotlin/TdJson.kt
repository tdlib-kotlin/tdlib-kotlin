package dev.whyoleg.td.json

import java.io.*

public actual sealed interface TdJson {
    public actual fun createClientId(): Int
    public actual fun execute(request: String): String?
    public actual fun send(clientId: Int, request: String)
    public actual fun receive(timeout: Double): String?
}

public actual fun TdJson(): TdJson {
    return TdJsonJni
}

private fun loadFromResources(name: String, extension: String) {
    val temp = tempFile(name, extension)

    val libraryStream =
        TdJsonJni::class.java.classLoader!!
            .getResourceAsStream("lib/$name.$extension")
            ?: error("No library '$name.$extension' found in jar")

    libraryStream.use {
        temp.outputStream().use {
            libraryStream.copyTo(it)
        }
    }

    System.load(temp.canonicalPath)
}

private fun tempFile(name: String, extension: String): File {
    val tempFile = File.createTempFile(name, extension)
    tempFile.deleteOnExit()
    return tempFile
}

private object TdJsonJni : TdJson {
    init {
        //TODO loading per platform
        // also suppress exceptions
        // android vs jvm loading
        runCatching {
            System.loadLibrary("tdjson")
        }.recoverCatching {
            loadFromResources("libtdjson", "so")
        }.onFailure {
            println("tslib: failed to load native library")
            it.printStackTrace()
        }

        runCatching {
            loadFromResources("libtdjsonapijni", "so")
        }.onFailure {
            println("tdjsonjni: failed to load native library")
            it.printStackTrace()
        }
    }

    external override fun createClientId(): Int
    external override fun execute(request: String): String?
    external override fun send(clientId: Int, request: String)
    external override fun receive(timeout: Double): String?
}
