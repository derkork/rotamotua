package com.ancientlightstudios.rotamotua.agents

import com.ancientlightstudios.rotamotua.api.SimpleAgent
import com.ancientlightstudios.rotamotua.runtime.Packet

class GeneratePacket(name: String) : SimpleAgent(name) {

    override fun onPacket(packet: Packet) = doWork()
    override fun onDisconnectedInput() = doWork()

    private fun doWork(){
        val count = getSetting("count").asInt(0)
        for (i in 0 until count) {
            writeEmptyPacket()
        }
    }


}