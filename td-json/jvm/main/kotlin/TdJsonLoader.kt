package dev.whyoleg.td.json

import java.util.*

public actual object TdJsonLoader {
    private val instances = mutableListOf<TdJson>()

    init {
        TdJsonServiceLoader::class.java
            .let { ServiceLoader.load(it, it.classLoader) }
            .forEach { load(it.instance) }
    }

    public actual fun load(instance: TdJson) {
        instances.add(instance)
    }

    public actual fun get(): TdJson {
        return instances.single() //TODO proper message
    }
}

public interface TdJsonServiceLoader {
    public val instance: TdJson
}
