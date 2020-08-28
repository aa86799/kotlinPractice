package com.stone.clazzobj.extension

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 17 28
 */
//扩展Any?的toString
fun Any?.toString(): String {
    if (this == null) return "null"
    return toString()
}

class R(id: Int) {
    private val id = id

    override fun toString(): String {
        return "R(id=$id)"
    }
}

private fun foo(arg: R?) {
    println(arg.toString())
}

fun main(args: Array<String>) {
    foo(null)
    foo(R(18))
}

