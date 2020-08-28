package com.stone.clazzobj.extension

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 19 44
 */

//class D {} //Statically.kt 定义了一个 D的 public类， 在同个package下不能重复定义

class DM {
    fun bar() {

    }
}
class DN {
    fun baz() {

    }
    /*
    在class 中 声明 DM类的 扩展函数
     */
    fun DM.foo() {
        bar() //DM bar
        baz() //DN baz
    }

    override fun toString(): String {
        return "DN"
    }

    /*
    在扩展函数中 调用 外部类的 函数 this@class
     */
    fun DM.to() {
        toString()
        this.toString() //这和上一句是一样的， 调用的是DM的
        this@DN.toString() //调用DN的
    }
}


open class DE
open class DF: DE()
open class CE {
    open fun DE.foo() {
        println("DE.foo in CE")
    }
    open fun DF.foo() {
        println("DF.foo in CE")
    }

    fun called(de: DE) {
        de.foo()
    }
}
class CF: CE() {
    override fun DE.foo() {
        println("DE.foo1 in CF")
    }

    override fun DF.foo() {
        println("DF.foo1 in CF")
    }
}

fun main(args: Array<String>) {
    CE().called(DE())  //DE.foo in CE
    CE().called(DF())  //DF、DE扩展的相同的方法； 子类扩展无效的。所以 DE.foo in CE

    CF().called(DE())  //DE.foo in CF
    CF().called(DF())  //DE.foo in CF
}

