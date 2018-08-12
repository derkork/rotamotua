package com.ancientlightstudios.rotamotua.agents

import com.ancientlightstudios.rotamotua.api.SimpleAgent
import com.ancientlightstudios.rotamotua.runtime.Packet
import mu.KotlinLogging

class Print(name: String) : SimpleAgent(name) {
    private val log = KotlinLogging.logger {}

    override fun onPacket(packet: Packet) {
        log.info { "[$name]: ${getSetting("text").wrapped}" }
        writeEmptyPacket()
    }

}