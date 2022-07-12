package com.example.appsdev.Core.Utils

import android.content.Context
import com.example.appsdev.R


@Suppress("UNCHECKED_CAST")
class NewSharedPreferences(
    private val context: Context,
    private val fileName : String,
    private val key : String,
){
    private val config = context.getSharedPreferences(fileName, 0)

    fun <T> saveSP(data : T){
        when(data!!::class.simpleName){
            "Int"    -> config.edit().putInt(key, data as Int).apply()
            "String" -> config.edit().putString(key, data as String).apply()
            "Float"  -> config.edit().putFloat(key, data as Float).apply()
            "Long"   -> config.edit().putLong(key, data as Long).apply()
            "Boolean"-> config.edit().putBoolean(key, data as Boolean).apply()
            else     -> context.toast("SharedPreferences no encontró: $data")
        }
    }

    fun <T> getSP(defaultVariable:T) : T{
        return when(defaultVariable!!::class.simpleName){
            "Int"    -> config.getInt(key,defaultVariable as Int) as T
            "String" -> config.getString(key,defaultVariable as String) as T
            "Float"  -> config.getFloat(key,defaultVariable as Float) as T
            "Long"   -> config.getLong(key,defaultVariable as Long) as T
            "Boolean"-> config.getBoolean(key,defaultVariable as Boolean) as T
            else -> defaultVariable
        }
    }
}

class LookingVariablesTypes<T>(variable: T) {
    var variableType = variable!!::class.simpleName
    fun showVariableType():String{
        return when(variableType){
            "Int"    -> "Se encontró un Entero"
            "String" -> "Se encontró un String"
            "Float"  -> "Se encontró un Float"
            "Double" -> "Se encontró un Double"
            "Long"   -> "Se encontró un Long"
            "Boolean"-> "Se encontró un Booleano"
            else     -> "Se encontró nueva variable: [$variableType]"
        }
    }
}

fun main(){
    /*val data1 = 0
    val info1 = data1::class.simpleName

    val data2 = "sd"
    val info2 = data2::class.simpleName

    val data3 = 0.0f
    val info3 = data3::class.simpleName

    val data4 = 0.0
    val info4 = data4::class.simpleName

    println(info1)
    println(info2)
    println(info3)
    println(info4)*/

    val data = LookingVariablesTypes(true)

}
