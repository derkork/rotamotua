package com.ancientlightstudios.rotamotua.runtime

class Variable private constructor(val name:String) {
    var value:Any? = null
    var expression:String = ""
    var isExpression:Boolean = false
    var isReadOnly:Boolean = false

    companion object {
        fun standard(name: String, defaultValue:Any?): Variable {
            val result = Variable(name)
            result.value = defaultValue
            return result
        }

        fun readonly(name: String, defaultValue:Any?): Variable {
            val result = Variable(name)
            result.value = defaultValue
            result.isReadOnly = true
            return result
        }

        fun expression(name:String, expression:String) : Variable {
            val result = Variable(name)
            result.isExpression = true
            result.expression = expression
            return result
        }
    }

}