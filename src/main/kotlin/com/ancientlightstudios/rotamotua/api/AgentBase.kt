package com.ancientlightstudios.rotamotua.api

import com.ancientlightstudios.rotamotua.runtime.*

abstract class AgentBase(override var name: String) : Agent, ValueProvider {
    private val internalSettings = mutableListOf<Variable>()
    private val settingsByName = mutableMapOf<String, Variable>()
    private var internalContext: Context? = null

    protected val context: Context
        get() = internalContext!!

    override val settings: List<Variable>
        get() = internalSettings

    override fun init(context: Context) {
        this.internalContext = context
        onInit()
    }

    fun addSetting(setting: Variable) {
        if (settingsByName.containsKey(setting.name)) {
            throw IllegalArgumentException("setting ${setting.name} already exists")
        }
        internalSettings.add(setting)
        settingsByName[setting.name] = setting
    }

    override fun get(name: String): Any? = getSetting(name).wrapped

    protected fun getSetting(name: String): EvaluatedValue {
        val setting = settingsByName[name] ?: return EvaluatedValue.EMPTY
        return context.evaluate(this, setting)
    }

    protected fun setSetting(name:String, value: Any?) {
        val setting = settingsByName[name] ?: throw IllegalStateException("no such setting: $name")
        if (setting.isExpression) {
            throw IllegalStateException("setting is an expression: $name")
        }
        setting.value = value
    }

    protected open fun onInit() {
    }
}