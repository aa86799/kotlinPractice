package com.stone.coroutine

import kotlinx.coroutines.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/7 11:15
 */
fun main(args: Array<String>) = runBlocking {

    fun test01() {
        launch(coroutineContext) {
            println("stone a".hashCode() shr 16)
        }
        //全局式. 协程的生命周期只受整个应用程序的生命周期的限制。
        GlobalScope.launch(Dispatchers.Unconfined) {
            println("hello, (${Thread.currentThread().name})")
            delay(1000L)
            println("stone  ${ Thread.currentThread().name }")
        }

        //阻塞式
        runBlocking {
            delay(1000L) //与上一个delay基本是同时执行的，只晚一点点。
            println("Did you win the lottery?")
        }

        //阻塞式 返回 T
        val v = runBlocking {
            delay(1500L)
            GlobalScope.launch {
                println("yeah. $2")
            }
            delay(500)
            "i win"
        }
        println(v)
    }
    test01()
//    Thread.sleep(3000L) // 阻塞主线程 2 秒钟来保证 JVM 存活;  当把main 转成  阻塞式协程后， 就不需要这句了

    //返回 Job; Job#join() 等待对应协程执行完毕
    val job = GlobalScope.launch {
        delay(1000L)
       launch {
           delay(8000)
           println(8/0)
       }
        println("World!")
    }
    job.cancel()
    println("Hello,")
//    job.join() // wait until child coroutine completes
    println("unprecedented ${job.isCompleted}")
    println("unprecedented ${job.isCancelled}")

    launch {
        delay(200L)
        println("Task from runBlocking")
    }

    /*
     * 除了不同构建器提供的协程范围之外，还可以使用coroutineScope构建器声明自己的范围。它创建一个协程范围，直到所有启动的子协程完成才会完成。
     */
    // Creates a coroutine scope
    coroutineScope {
        launch {
            delay(500L)
            println("Task from nested launch ${coroutineContext[Job]} ${ Thread.currentThread().name }")
        }
        delay(100L)
        println("Task from coroutine scope ${coroutineContext[Job]}") // This line will be printed before the nested launch
    }

    // 挂起函数中，调用其它挂起函数；挂起协程
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
        repeat(10) { i ->
            print("${i}\t")
        }
        println()
    }
    //协程中 调用挂起函数
    val deferred1 = async {
        doWorld()
        "done ${Thread.currentThread().name}"
    }
    println(deferred1.await())

    val deferred2 = async(start = CoroutineStart.LAZY) {
        println("时间：${System.currentTimeMillis()}")
        "done ${Thread.currentThread().name}"
    }
    delay(3000)
    println(deferred2.await())
}
