package com.ancientlightstudios.rotamotua.api

import com.ancientlightstudios.rotamotua.runtime.Packet
import com.ancientlightstudios.rotamotua.runtime.WorkResult

abstract class SimpleAgent(name: String) : AgentBase(name) {

    final override fun work(): WorkResult {
        if (!context.isInputConnected(this)) {
            onDisconnectedInput()
            return WorkResult.Finished
        }

        if (context.withInput(this) {packet -> onPacket(packet)}) {
            return WorkResult.Working
        }

        return WorkResult.Idle
    }


    protected open fun onDisconnectedInput() {}
    protected open fun onPacket(packet: Packet) {}

    protected fun writeEmptyPacket() {
        context.writeToOutput(this, Packet.EMPTY)
    }
}