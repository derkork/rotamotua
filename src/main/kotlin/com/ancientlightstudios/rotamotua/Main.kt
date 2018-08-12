package com.ancientlightstudios.rotamotua

import com.ancientlightstudios.rotamotua.agents.Counter
import com.ancientlightstudios.rotamotua.agents.GeneratePacket
import com.ancientlightstudios.rotamotua.agents.ListDirectory
import com.ancientlightstudios.rotamotua.agents.Print
import com.ancientlightstudios.rotamotua.runtime.Context
import com.ancientlightstudios.rotamotua.runtime.Variable

fun main(args: Array<String>) {

    System.setProperty("idea.io.use.fallback", "true")

    val context = Context()

    val generator = GeneratePacket("makePackets")
    generator.addSetting(Variable.standard("count", 5))

    val generator2  = GeneratePacket("makePackets2")
    generator2.addSetting(Variable.standard("count", 5))


    val debugger = Print("debugger")
    debugger.addSetting(Variable.expression("text", "_input"))

    val counter = Counter("counter")

    val counterPrinter = Print("counterPrinter")
    counterPrinter.addSetting(Variable.expression("text", "counter.count"))

    val listDirectory = ListDirectory("listDirectory")
    listDirectory.addSetting(Variable.standard("folder", """C:\Users\kork\Documents"""))

    val directoryPrinter = Print("directoryPrinter")
    directoryPrinter.addSetting(Variable.expression("text", "_input.path"))

//    context.add(generator)
//    context.add(generator2)
//    context.add(counter)
//    context.add(debugger)
//    context.add(counterPrinter)
    context.add(listDirectory)
    context.add(directoryPrinter)

    context.connect(generator, debugger)
    context.connect(generator, generator2)
    context.connect(generator2, counter)
    context.connect(counter, counterPrinter)
    context.connect(listDirectory, directoryPrinter)

    context.run()
}