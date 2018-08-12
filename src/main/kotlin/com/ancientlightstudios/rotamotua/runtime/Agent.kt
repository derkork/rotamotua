package com.ancientlightstudios.rotamotua.runtime

interface Agent : ValueProvider {

    val name:String
    val settings: List<Variable>
    fun init(context: Context)

    fun work(): WorkResult

    fun outputQueueName(output:String) = "$name.outputs.$output"
    fun inputQueueName(input:String) = "$name.inputs.$input"
}