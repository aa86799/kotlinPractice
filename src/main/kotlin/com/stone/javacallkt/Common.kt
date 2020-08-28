@file:JvmName("CalcUtil") //provide class Name with top level fun
package com.stone.javacallkt

import java.io.IOException


/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 15/06/2017 19 48
 */
const val const1 = 1

class Common {
//    const var const2 = 2  //const not applicable var
//    const val const2 = 2  //const val  are only allowed on top level or in objects

    val prop1 = "111"  //java call: getter
    var prop2 = "222"  //java call: getter、setter
    @JvmField var prop3 = "333" //java call:  prop3

    companion object {
        val prop4 = 444  //java call: getter  in Common.companion
        var prop5 = 555  //java call: getter、setter   in Common.companion
        @JvmStatic var prop6 = 666 //java call: prop6   in Common.companion
        @JvmField val prop7 = 777 //java not call
        const val const3 = 3

        @JvmStatic fun staticMethod() {}
    }

    inner class InnerClass {
        @JvmField val prop8 = 8
        var prop9 = 9
//        const val prop10 = 10

//        @JvmStatic fun staticMethod1() {} //must in object class or companion object

    }

    object ObjClass {
        const val const4 = 10
        @JvmStatic fun staticMethod() {}
    }

//    @JvmStatic fun staticMethod() {} //must in object class or companion object

    fun List<String>.filterValid(): List<String>? {
        return null
    }

    @JvmName("filterValidInt") //the previous method and this are same signature in jvm, so use @JvmName to differentiates
    fun List<Int>.filterValid(): List<Int>? {
        return null
    }

    fun extensionFun() {
        listOf("aa").filterValid()
        listOf(1).filterValid()
    }

    //********* @JvmName
    val x: Int
        get() = 15
    @JvmName("getX_prop") // declare any one of them (x prop, fun getX)
    fun getX() = 10

    fun gainX() {
        getX()
        val a = x
    }

    //********* @JvmOverloads
    @JvmOverloads fun f(a: String, b: Int = 0, c: String = "abc") {//b c  both have default value
        /*
        use @JvmOverloads, then in jvm:
        void f(String a) { }
        void f(String a, int b) { }
        void f(String a, int b, String c) { }
         */
    }

    fun testF() {
        f("")
        f("", 1)
        f("", 1, "")
    }

    fun foo() {
        throw IOException()
    }

    @Throws(IOException::class) //java call, throw Exception
    fun foo2() {
        throw IOException()
    }
}

object Singleton {
    @JvmField var ps1 = 2
//    lateinit var ps2: Int //lateinit  not allowed on primitive type
    lateinit var ps2: Common
}

object ObjClass {
    const val const5 = 4
    @JvmStatic fun staticMethod() {}
}

fun add(n1: Int, n2: Int) {
    println(n1 + n2)
}

fun main(args: Array<String>) {
    Test.callKot()
}

