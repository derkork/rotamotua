package com.ancientlightstudios.rotamotua.runtime

interface ValueProvider {
    operator fun get(name:String): Any?
}