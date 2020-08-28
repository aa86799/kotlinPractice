package com.stone.clazzobj.abstractclaz

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 22/06/2017 13 54
 */
abstract class AbsA

abstract class AbsB internal constructor(a: Int): AbsA() {
    var prop1: Int = a
    val prop2: Int = 999
        get() = field

    abstract fun absMethod()

    fun normalMethod() {

    }

    open fun OutsideMethod() {

    }
}

class SubA: AbsA()

class SubB(v: Int) : AbsB(v) {

    override fun absMethod() {

    }

    override fun OutsideMethod() {
        super.OutsideMethod()
    }
}

fun main(args: Array<String>) {
    val a = object : AbsA() { }

    val b = object : AbsB(1688) {
        override fun absMethod() {

        }
    }

    val c = SubA()

    val d = SubB(1699)
}