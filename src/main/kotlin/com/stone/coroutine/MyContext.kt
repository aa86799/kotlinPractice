package com.stone.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/3/14 14:18
 */
class MyContext(key: CoroutineContext.Key<*>) : AbstractCoroutineContextElement(key) {
    override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
        return super.fold(initial, operation)
    }

    override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
        return super.minusKey(key)
    }

    override fun plus(context: CoroutineContext): CoroutineContext {
        return super.plus(context)
    }


}

data class Temp(val txt: String)

fun main() {

    val c = "admin"
    println(c)
    val cc = String("admin2".toByteArray())
    println(cc)

    fun test(v: Int, block: Int.()  -> Double) {//后面 传参用 this
//    fun test(v: Int, block: (Int)  -> Double) {//后面 传参用 it
        println(block(v * 2)) //println 1024.0
    }
    fun calc(a: Int): Double {
        return (a shl 10) * 1.0 //1<<10 = 1024
    }
    test(1) {
        calc(this)
//        18.5
    }


    GlobalScope.launch {
        async {  }
        launch {  }
        withContext(coroutineContext) {

        }
    }
    runBlocking {

    }

    MyContextScope(Dispatchers.Default).launch {

    }
//    MainScope().async {
//
//    }

    val xc = GlobalScope.launch(Dispatchers.Default, CoroutineStart.LAZY) {
        println("${Thread.currentThread().name}")
    }
    Thread {
        println("${Thread.currentThread().name}")
        GlobalScope.launch(/*Dispatchers.Default*/) {
            println("${Thread.currentThread().name}")
            delay(2000)
        }
        GlobalScope.async(/*Dispatchers.Default*/) {
            println("${Thread.currentThread().name}")
        }
    }.start()
}

class MyContextScope(override val coroutineContext: CoroutineContext) : CoroutineScope {

}