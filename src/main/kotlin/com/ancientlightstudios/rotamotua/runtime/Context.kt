package com.ancientlightstudios.rotamotua.runtime

import java.util.*
import java.util.concurrent.LinkedTransferQueue
import javax.script.Bindings
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

class Context {
    private val queues = mutableMapOf<String, Queue<Packet>>()

    private val evaluationEngine = ScriptEngineManager().getEngineByName("luaj")
    private val evaluationContextsByAgent = mutableMapOf<Agent, Bindings>()
    private val agents = mutableListOf<Agent>()

    fun add(agent: Agent) {
        evaluationContextsByAgent[agent] = evaluationEngine.createBindings()
        agents.add(agent)
        evaluationEngine.put(agent.name, LuaValueProviderShim(agent))
        agent.init(this)
    }

    fun connect(source:Agent, destination: Agent) = connect(source, "output", destination, "input")

    fun connect(source: Agent, output: String, destination: Agent, input: String) {
        getOrCreateOutput(source, output).addReceiver(getOrCreateInput(destination, input))
    }

    private fun getOrCreateOutput(source: Agent, output: String): DistributingQueue<Packet> =
            queues.getOrPut(source.outputQueueName(output)) { DistributingQueue() } as DistributingQueue<Packet>

    private fun getOrCreateInput(destination: Agent, input: String) = queues.getOrPut(destination.inputQueueName(input)) { LinkedTransferQueue() }

    private fun writeToQueue(name: String, packet: Packet) {
        val queue = queues[name] ?: return
        queue.offer(packet)
    }

    fun writeToOutput(agent: Agent, packet:Packet) {
        writeToOutput(agent, "output",  packet)
    }

    fun writeToOutput(agent: Agent, name: String, packet: Packet) {
        writeToQueue(agent.outputQueueName(name), packet)
    }

    fun hasInput(agent: Agent, name:String) :Boolean {
        val queue = queues[agent.inputQueueName(name)] ?: return false
        return !queue.isEmpty()
    }

    fun isInputConnected(agent: Agent, name:String = "input") : Boolean {
        return queues.containsKey(agent.inputQueueName(name))
    }

    fun withInput(agent: Agent, perform: (Packet) -> Unit) = withInput(agent, "input", perform)
    
    fun withInput(agent: Agent, name: String, perform: (Packet) -> Unit): Boolean {
        val queue = queues[agent.inputQueueName(name)] ?: return false
        val next = queue.poll()
        if (next != null) {
            setScriptingVariable(agent, "_$name", LuaValueProviderShim(next))
            perform(next)
            setScriptingVariable(agent, "_$name", null)
            return true
        }
        return false
    }

    fun evaluate(agent: Agent, setting: Variable): EvaluatedValue {
        if (!setting.isExpression) {
            return EvaluatedValue(setting.value)
        }

        val bindings = evaluationContextsByAgent[agent]!!
        bindings.putAll(evaluationEngine.getBindings(ScriptContext.ENGINE_SCOPE))
        val script = "return ${setting.expression}"
        val result = evaluationEngine.eval(script, bindings)
        return EvaluatedValue(result)
    }

    private fun setScriptingVariable(agent: Agent, name:String, value:Any?) {
        evaluationContextsByAgent[agent]!![name] = value
    }

    fun run() {
        val activeAgents = mutableSetOf<Agent>()
        val finishedAgents = mutableSetOf<Agent>()

        activeAgents.addAll(agents)

        var nextRound: Boolean

        do {
            nextRound = false
            for (agent in activeAgents) {
                val result = agent.work()
                if (result == WorkResult.Working) {
                    nextRound = true
                }
                if (result == WorkResult.Finished) {
                    finishedAgents.add(agent)
                }
            }
            activeAgents.removeAll(finishedAgents)
            if (!nextRound) {
                nextRound = hasPendingData()
            }
        } while (nextRound)
    }

    private fun hasPendingData(): Boolean = queues.values.any {
        return it !is DistributingQueue<*> && !it.isEmpty()
    }
}