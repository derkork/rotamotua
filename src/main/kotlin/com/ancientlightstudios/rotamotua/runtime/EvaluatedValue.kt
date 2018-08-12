package com.ancientlightstudios.rotamotua.runtime

class EvaluatedValue(val wrapped: Any?) {

    companion object {
        val EMPTY = EvaluatedValue(null)
    }
    fun asInt(): Int {
        var result: Int? = null
        if (wrapped is String) {
            result = wrapped.toIntOrNull()
        }

        if (wrapped is Number) {
            result = wrapped.toInt()
        }

        return result ?: throw IllegalStateException("not an int")
    }

    fun asInt(defaultValue: Int): Int {
        return try {
            asInt()
        } catch (ex: IllegalStateException) {
            defaultValue
        }
    }

    fun asString(defaultValue: String): String {
        if (wrapped != null ) {
            return wrapped.toString()
        }
        return defaultValue
    }

}
