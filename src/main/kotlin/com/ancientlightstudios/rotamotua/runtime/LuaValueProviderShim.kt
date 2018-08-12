package com.ancientlightstudios.rotamotua.runtime

import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.jse.CoerceJavaToLua

class LuaValueProviderShim(val provider: ValueProvider) : LuaTable() {

    init {
        setmetatable(this)
        rawset(LuaValue.INDEX, object : TwoArgFunction() {
            override fun call(table: LuaValue, key: LuaValue): LuaValue {
                if (key.isstring()) {
                    return toLua(provider[key.tojstring()])
                }
                return toLua(null)
            }
        })
    }

    private fun toLua(javaValue: Any?): LuaValue {
        return if (javaValue == null)
            LuaValue.NIL
        else javaValue as? LuaValue ?: CoerceJavaToLua.coerce(javaValue)
    }
}