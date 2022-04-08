package dev.whyoleg.td.json

public actual object TdJsonLoader {
    private val instances = mutableListOf<TdJson>()

    public actual fun load(instance: TdJson) {
        instances.add(instance)
    }

    public actual fun get(): TdJson {
        return instances.single() //TODO proper message
    }
}
