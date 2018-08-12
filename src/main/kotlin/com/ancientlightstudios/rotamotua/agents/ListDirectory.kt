package com.ancientlightstudios.rotamotua.agents

import com.ancientlightstudios.rotamotua.api.SimpleAgent
import com.ancientlightstudios.rotamotua.runtime.Packet
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.InvalidPathException
import java.nio.file.Paths

class ListDirectory(name: String) : SimpleAgent(name) {

    private val log = KotlinLogging.logger {}
    override fun onDisconnectedInput() = doWork()
    override fun onPacket(packet: Packet) = doWork()

    private fun doWork() {
        val folder = getSetting("folder").asString("")
        val folderPath = try {
            Paths.get(folder)
        } catch (e: InvalidPathException) {
            log.warn { "'$folder' is not a valid path" }
            return
        }

        if (!Files.isDirectory(folderPath)) {
            log.warn { "'$folder' is not a directory" }
        }

        Files.list(folderPath).forEach { context.writeToOutput(this, Packet(mapOf("path" to it.toString()))) }
    }
}