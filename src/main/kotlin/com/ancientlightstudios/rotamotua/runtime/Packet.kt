package com.ancientlightstudios.rotamotua.runtime

class Packet (val contents:Map<String,Any?>) : ValueProvider {
    companion object {
        val EMPTY = Packet(emptyMap())
    }

    override fun get(name: String): Any? {
        return contents[name]
    }
}