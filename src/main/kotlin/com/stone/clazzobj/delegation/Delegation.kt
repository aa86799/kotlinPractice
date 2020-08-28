package com.stone.clazzobj.delegation

/**
 * desc  : 委托类
 * author: stone
 * email : aa86799@163.com
 * time  : 13/07/2017 09 37
 */

interface Base {
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override fun print() {
        print(x)
    }
}

class Derived(b: Base) : Base by b {
    fun add() {

    }
}

fun main(args: Array<String>) {
    val b = BaseImpl(10)
    Derived(b).print() // prints 10
    Derived(b).add()
}
