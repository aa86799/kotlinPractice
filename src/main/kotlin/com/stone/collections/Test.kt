package com.stone.collections

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/9 23:26
 */
fun main() {
    val list = mutableListOf<List<Int>>()
    for (i in 0..5) {
        list.add(listOf(i*1, i*2))
    }
    list.flatten().forEach {
        println(it)
    }
//    list.flatMap { it }.forEach { println(it) }  //相当于 flatten()
    list.flatMap {
        val newList = mutableListOf<String>()
        for (i in it) {
            newList.add("stone->${i*10}")
        }
        newList
    }.forEach { println(it) }

    runBlocking {
        launch {
            delay(2000)
            println("中华人民共和国2")
        }
        launch {
            delay(1000)
            println("中华人民共和国1")
        }
    }
}