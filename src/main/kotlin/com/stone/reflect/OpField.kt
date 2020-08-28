package com.stone.reflect

import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/6/3 11:40
 */
fun main() {
//    test1()
    test2()

    val list = listOf(false, true, false, true, false, true)
    println(list.sortedBy { it })
}

private fun test1() {
    val im = "admin"
    val field = String::class.java.getDeclaredField("hash")
    field.isAccessible = true
    val len = field.get(im)
    if (len != null) {
        println(len)
        field.set(im, 0xCFD0ABC)
        println(field.get(im) == 217909948)
        field.isAccessible = false
    }
}

private fun test2() {
    val t = "stone"
    String::class.declaredMemberProperties.forEach {
        println("${it.name}=${it.get(t)}")
    }
    println("-----")
    String::class.declaredMembers.forEach {
        println(it.name)
    }
    println("-----")
    String::class.declaredFunctions.forEach {
        println(it.name)
//        println(it.isFinal)
    }
}