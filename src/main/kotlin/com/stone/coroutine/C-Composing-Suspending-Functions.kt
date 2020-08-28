package com.stone.coroutine

import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/7 17:34
 */
class `C-Composing-Suspending-Functions` {
}

fun main() = runBlocking {
    suspend fun doSomethingUsefulOne(): Int {
        delay(500L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(500L) // pretend we are doing something useful here, too
        return 29
    }

    //调用一个挂起函数后，再调用另一个，顺序执行
    val time = measureTimeMillis {
//    val time = measureNanoTime {//计算耗时，微秒单位
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
    }
    println("0.Completed in $time ms")

    /*
     * async 与 launch 类似，它启动一个单独的协同程序，这是一个轻量级线程，与所有其他协同程序并发工作。
     * async 是异步的，返回 Deferred<T> ；launch 返回 Job；Deferred<T> : Job
     * Deferred 是异步、可取消，含有结果。通过 挂起函数 await() 获取它的最终结果。
     * 如下，在打印方法中调用两上 Deferred对象的await() 实现了并发，提高了运行速度。
     *
     * 而如果，直接 one = async{one()}.await() ，再 two = ...；则没有并发。
     * 因为 await()是挂起函数，one挂起，直接到有了结果后；才执行two，那就是顺序执行了。
     */
    val time1 = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        println("The answer is ${one.await() + two.await()}")
    }
    println("1.Completed in $time1 ms")

    /*
     * async(start=CoroutineStart.LAZY) 该start选项，为CoroutineStart.LAZY时，
     * 在这种模式下，只在await()需要其结果或调用其job的start()时启动协同程序。
     *
     * 注意，如果我们只是在println中调用await，而不首先调用单个协程的start，这将导致顺序行为，因为await会启动协程执行并等待它的完成。
     * 而上一个例子中，没有传start参数，是有默认的值为 CoroutineStart.DEFAULT，它会立即调度执行协程。所以，不需要 one.start()、two.start()。
     */
    val time2 = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // some computation
        one.start() // start the first one
        two.start() // start the second one
        println("The answer is ${one.await() + two.await()}")
    }
    println("2.Completed in $time2 ms")

    /*
     * 全局范围的 异步协程。
     * 当进程没有被停止、销毁时，全局协程就会执行。所以，在使用全局式的 协程时 要小心。
     */
    fun somethingUsefulOneAsync() = GlobalScope.async {
        doSomethingUsefulOne()
    }
    fun somethingUsefulTwoAsync() = GlobalScope.async {
        doSomethingUsefulTwo()
    }
    val time3 = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("3.Completed in $time3 ms")

    /*
     * 将异步协程与其结果，组合成一个 挂起函数。
     * 这时在挂起函数的代码中出现错误并抛出异常，那么在其作用域中启动的所有协程都将被取消。
     * 注意，非CancellationException 可能会崩溃进程，需要 catch。
     */
    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
//        throw IllegalAccessException("aaa")
        one.await() + two.await()
    }
    concurrentSum()

    /*
     * 协程two抛出异常后，就会取消父协程，再而取消 one 协程。
     * one 协程，检查到取消异常后，执行 finally{}。 所以结果会输出 Second... 再 First...
     */
    suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // Emulates very long computation
                42
            } finally {
                println("First child was cancelled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }
    try {
        failedConcurrentSum()
    } catch (e: Exception) {

    }

    println()
}