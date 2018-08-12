package com.ancientlightstudios.rotamotua.agents

import com.ancientlightstudios.rotamotua.api.SimpleAgent
import com.ancientlightstudios.rotamotua.runtime.Packet
import com.ancientlightstudios.rotamotua.runtime.Variable

class Counter(name: String) : SimpleAgent(name) {

    override fun onInit() {
        addSetting(Variable.readonly("count", 0))
    }

    override fun onPacket(packet: Packet) {
        setSetting("count", getSetting("count").asInt()+ 1)
        writeEmptyPacket()
    }

}