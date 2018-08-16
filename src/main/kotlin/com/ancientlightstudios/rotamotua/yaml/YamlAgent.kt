package com.ancientlightstudios.rotamotua.yaml

import com.fasterxml.jackson.annotation.JsonAnySetter

class YamlAgent(val type: String = "", val name: String = "", private val connect: Any?) {
    private val internalSettings = mutableMapOf<String, Any?>()
    val settings by lazy { internalSettings.toMap() }
    val connections: List<YamlConnection> by lazy { computeConnections() }


    @JsonAnySetter
    private fun onProperty(name: String, value: Any?) {
        internalSettings[name] = value
    }


    private fun computeConnections(): List<YamlConnection> {
        if (connect == null) {
            return emptyList()
        }

        if (connect is List<*>) {
            return connect.map { YamlConnection.parse(it.toString()) }
        }

        return listOf(YamlConnection.parse(connect.toString()))
    }

}