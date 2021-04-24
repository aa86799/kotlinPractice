package com.stone.coroutine

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.random.Random

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2020/12/27 06:48
 */
class ThreadTest {

    suspend fun thread1() = coroutineScope {
        Thread {
            Thread.sleep(Math.pow(Random.nextLong() % 3 * 1.0, 2.0).toLong() * 1000)
            println("thread1")
        }.start()
    }

    suspend fun thread2() = coroutineScope {
        Thread {
            Thread.sleep(Math.pow(Random.nextLong() % 4 * 1.0, 1.5).toLong() * 1000)
            println("thread2")
        }.start()
    }

//    suspend fun thread3(): ReceiveChannel<> {
    suspend fun thread3() = suspendCoroutine<Int> {
        Thread {
            Thread.sleep(Math.pow(Random.nextLong() % 4 * 1.0, 1.5).toLong() * 1000)
            println("thread1")
            it.resume(3)
        }.start()
    }

    suspend fun thread4() = suspendCoroutine<Int> {
        Thread {
            try {
                Thread.sleep(Math.pow(Random.nextLong() % 4 * 1.0, 1.5).toLong() * 1000)
                println("thread2")
                it.resume(4)
            } catch (e: Throwable) {
                it.resumeWithException(e)
            }

        }.start()
    }

    suspend fun testComposeThread() {
//        thread1()
//        thread2()
        // 即使 = coroutineScope {}  也不是 一定顺序执行的。

        /*flow<Int> {
            thread1()
            emit(1)
            thread2()
            emit(2)
        }.collect { value ->
            println("get: $value\t")
        }*/
        //flow 还是适合 操作 集合式的，会先连续emit并collect。

        thread3()
        thread4()
    }
}

fun main() = runBlocking {
    val test = ThreadTest()
    test.testComposeThread()
}