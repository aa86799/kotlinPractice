package com.stone.coroutine

import kotlinx.coroutines.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/7 16:25
 */
fun main() = runBlocking<Unit> {
    val job = launch {
        repeat(10) {
            print("$it\t")
            delay(20)
//            yield() //屈服/放弃; cpu执行切换
        }
    }
    delay(8)
    job.cancel() //取消协程。协程中的所有挂起函数都是可取消的。它们检查协程的取消，并在取消时抛出CancellationException。
    job.join()
    //取消协程，是协作的，需要子协程、子挂起函数执行前后，去检查『取消』状态，并在取消时抛出CancellationException。
    println()

    /*
     * 但是，如果协同程序在计算中工作，并且没有检查(无挂起函数、无子协程)是否取消，那么它就不能取消。
     * 如下面的例子所示，使用 Job#isActive 属性来判断。
     * Job 还有两个属性：isCompleted、isCancelled
     */
    val startTime = System.currentTimeMillis()
    val job1 = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // computation loop, just wastes CPU; 这时不能取消
//        while (isActive) {//协程是处于活动状态(非完成且非取消状态); 这时可以取消
            // print a message twice a second
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("job1: I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    job1.cancelAndJoin() //内部就是先cancel()再join()。等待取消操作完成

//    Closing resources with finally. 在取消后，关闭资源。使用 try {} finally {}
    val job2 = launch {
        try {
            repeat(1000) { i ->
                println("job2: I'm sleeping $i ...")
                delay(20)
            }
        } finally {
            println("job2: I'm running finally")
            //通常关闭操作是非阻塞的。可以使用 挂起函数 withContext 传 NonCancellable 这个 context，达到在被取消的协程，再挂起的一个函数体的效果。
            withContext(NonCancellable) {
                delay(1000L)
                println("job: And I've just delayed for 1 sec because I'm non-cancellable")
            }
        }
    }
    delay(100)
    job2.cancelAndJoin()

    //超时 挂起函数，在超时后，会报 TimeoutCancellationException，它是 CancellationException 的子类。
    //在协程内部 抛出 异常后， 协程会 catch到该异常，并协作 递归取消该协程及其所有子协程。
    //但若在 main()中直接使用，它会引起进程崩溃、停止。通常一般不需要过多考虑它，因为要这样调用，需要 main()是协程式实现。
//    withTimeout(1300) {
//        repeat(1000) {
//            println(it)
//            delay(500)
//        }
//    }

    //协程内超时，不会引起崩溃。只是取消
    launch {
        withTimeout(1300) {
            repeat(1000) {
                println(it)
                delay(500)
            }
        }
        print("withTimeout")
    }

    //withTimeoutOrNull 超时返回null，而不抛出异常。
    val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
            println("I'm sleeping $i ...")
            delay(500L)
        }
        "Done" // will get cancelled before it produces this result
    }
    println("Result is $result")
}