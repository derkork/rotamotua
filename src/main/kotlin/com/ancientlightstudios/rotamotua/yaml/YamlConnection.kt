package com.ancientlightstudios.rotamotua.yaml

class YamlConnection(val output: String, val target: String, val input: String) {
    companion object {
        fun parse(value: String): YamlConnection {
            val parts = value.split("\\s*->\\s*".toRegex(), limit = 2)
            if (parts.size == 1) {
                return YamlConnection("output", value, "input")
            }
            val inputDescriptorParts = parts[1].split(".", limit = 2)
            if (inputDescriptorParts.size == 1) {
                return YamlConnection(parts[0], parts[1], "input")
            }
            return YamlConnection(parts[0], inputDescriptorParts[0], inputDescriptorParts[1])
        }
    }
}