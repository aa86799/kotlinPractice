package com.stone.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/11 19:10
 */
fun main() = runBlocking {
    /*
     * Deferred 值，提供了一种方便的途径来在协程之间传输单个值。
     * Channel 提供了一种传输 streams value 的途径。
     *  在概念上类似 BlockingQueue。相对于BlockingQueue的put、take，它不是阻塞的，而是挂起的操作 send、receive。
     */
    val channel = Channel<Int>()
    launch {
        // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
        for (x in 1..5) channel.send(x * x)
    }
// here we print five received integers:
    repeat(5) { print("${channel.receive()} \t") }
    println("Done!")

    /*
     * 通道可以使用 close() 进行关闭。
     * 以发送端，isClosedForSend 在 close()之后返回true；
     * 在接收商，isClosedForReceive 在 接收完所有发的送的之后，才返回true。
     *
     * 若有发送，没有以接收操作，那么
     */
    val channel2 = Channel<Int>()
    launch {
        for (x in 1..5) {
            if (!channel2.isClosedForSend) {
                channel2.send(x * x)
            }
            if (x > 2) {
                channel2.close() // we're done sending
            }
        }
    }
    println(channel2.isClosedForReceive)
    repeat(3) { print("${channel2.receive()} \t") } //使用 for迭代也可以当成 收集操作。
//        for (y in channel2) print("$y \t")
    println(channel2.isClosedForReceive)
    println("2.Done!")

    /*
     * 典型的生产者消费者模型。
     * produce {} 内部创建协程，返回一个 ReceiveChannel ，该类型是 Channel 的父接口；
     *      {} 就可以使用 channel 的操作，如下使用了 send操作。
     * 使用 consumeEach，在消费端不需要显式的迭代了，内部有迭代处理。
     */
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (x in 1..5) send(x * x)
    }
    val squares = produceSquares()
    squares.consumeEach { print("3--$it \t") }
//    squares.consume { for(x in squares) print("3--$x \t") }
    println("3.Done!")

    /*
     * Pipelines 管道。管道模式是 一个协程作为生产者。
     * 对CoroutineScope扩展两个函数，使用 produce {} ，
     * 一个向channel send 无限send元素；
     * 一个迭代消费channel中的所有元元素，然后经过一定处理后，再次send；
     * 迭代消费 channel#receive() ；
     * 最后用用主协程context，取消所有孩子。结果说明，对produce实现中的挂起函数进行取消，当然是可以的。
     *
     * 综上，创建协程的所有函数都被定义为CoroutineScope上的扩展，
     * 因此我们可以依赖结构化并发来确保我们的应用程序中没有遗留的全局协程。
     */
    fun CoroutineScope.produceNumbers() = produce<Int> {
        var x = 1
        while (true) send(x++) // infinite stream of integers starting from 1
    }
    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<String> = produce<String> {
        for (x in numbers) send("${x * x}-${Thread.currentThread().name}")
    }
    val numbers = produceNumbers() // produces integers from 1 and on
    val squareNumbers = square(numbers) // squares integers
    for (i in 1..5) println(squareNumbers.receive()) // print first five
    println("Done!-${Thread.currentThread().name}") // we are done
    coroutineContext.cancelChildren() // cancel children coroutines

    /*
     * 下例，使用管道，输出从2开始的连续10个质数。流程非常复杂。
     * 首先需要了解，channel的send()后，没有相应的消费、关闭操作，会导致外部协程的挂起。
     *
     * cur = numbersFrom(2)， 从2开始send， 设此协程为c1； for (i in 1..10) {
     *   i=1, cur.receive()，先取到2，现在channel中为空，设此channel为ch1；filter ch1，由于为空，内部循环没操作，整体直接返回一个新的空的channel  ch2，此时协程设为c2 。
     *   i=2，cur.receive() 即从 ch2中接收，由于ch2是空的，会挂起，等待send；ch1 会send(3)，此时ch1 已作为参数，传给了上个产生 ch2的 produce 协程 c2，
     *          之后比较有意思的是，c2中的循环又开始执行了：是因为之前ch1中没有值，这里迭代就挂起了？还是ch1中有新的值后，本produce重新执行了？
     *              假设1成立：若挂起，父协程继续执行，没毛病。
     *
     *          c2中通过if，再send(3)，从最终输出结果看，"filter: 3" 和 "filter send:3" 相继输出；
     *          表示，c2中的循环又开始执行了，(很大可能就是，之前ch1为空时，挂起了)。
     *          send(3)，send后，又空了，挂起；
     *          继续新的 filter()，产生新的 channel ch3，协程 c3，  此时 c3( c2(c1) )，
     *          
     *
     *
     */
    fun CoroutineScope.numbersFrom(start: Int) = produce<Int> {
        var x = start
        while (true) {
            println("from send:$x")
            send(x++)
            delay(1000)
        } // infinite stream of integers from start   2,3,4,...
    }
    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, prime: Int) = produce<Int> {
        for (x in numbers) {
            println("filter :$x")
            if (x % prime != 0) {
                println("filter send:$x")
                send(x)
            }
        }
    }
    var cur = numbersFrom(2)
    for (i in 1..10) {
        println("a. $cur")
        val prime = cur.receive()
        println("接收到的-$prime --- ${cur.isEmpty}")
        cur = filter(cur, prime)
        println("b. $cur ---- ${cur.isEmpty}")
    }
    coroutineContext.cancelChildren() // cancel all children to let main finish

    println()
}