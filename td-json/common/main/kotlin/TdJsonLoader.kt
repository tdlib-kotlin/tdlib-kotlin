package dev.whyoleg.td.json

//internal
public expect object TdJsonLoader {
    public fun load(instance: TdJson)
    public fun get(): TdJson
}
