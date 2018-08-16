package com.ancientlightstudios.rotamotua.yaml

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URL

object YamlParser {

    fun parsePlan(url: URL) : YamlPlan {
        val mapper = ObjectMapper(YAMLFactory()).registerKotlinModule()
        return mapper.readValue(url, YamlPlan::class.java)
    }
}