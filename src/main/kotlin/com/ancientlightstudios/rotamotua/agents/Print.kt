package com.ancientlightstudios.rotamotua.agents

import com.ancientlightstudios.rotamotua.api.SimpleAgent
import com.ancientlightstudios.rotamotua.runtime.Packet
import mu.KotlinLogging

class Print(name: String) : SimpleAgent(name) {
    private val log = KotlinLogging.logger {}


    override fun onDisconnectedInput() {
        doWork()
    }

    override fun onPacket(packet: Packet) {
        doWork()
    }

    fun doWork() {
        log.info { "[$name]: ${getSetting("text").wrapped}" }
        writeEmptyPacket()
    }

}