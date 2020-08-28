package com.stone.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/8 21:37
 */
/*
 * Flow 异步流
 * 挂起函数能返回一个异步值，如何返回多个异步计算的值呢？ Flow 可以。
 */

fun main() = runBlocking {
    fun foo(): List<Int> = listOf(1, 2, 3)
    foo().forEach { value -> print("$value\t") }
    println()

    fun foo2(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..3) {
            Thread.sleep(500) // pretend we are computing it
            yield(i) // yield next value    为正在构建的[迭代器]生成一个值并挂起, 直到请求下一个值。
        }
        yieldAll(5..7)
    }
    foo2().forEach { value -> print("foo2.$value\t") }
    println("foo2()")

    /*
     * 协程内部调用挂起函数，不会阻塞主线程
     */
    launch {
        suspend fun foo3(): List<Int> {
            delay(1000) // pretend we are doing something asynchronous here
            return listOf(1, 2, 3)
        }
        foo3().forEach { value -> println("foo3.$value\t") }
        println("foo3()")
    }
    println("foo3().after")

    /*
     * 异步计算的值流, emit发射每一个数据；collect 收集每一个数据。
     * 与上一例的区别：
     * Flow 类型的 生成器 使用 flow 函数；
     * flow {} 内的代码，可以有挂起函数，如delay()； 因为 flow 的参数就是一个 扩展的 挂起函数（挂起函数内可以有挂起函数）；
     * 函数 foo4 不再被标记为 suspend；
     * 会阻塞主线程(父协程)，因没有子协程。
     */
    fun foo4(): Flow<Int> = flow { // flow builder
        for (i in 1..3) {
            delay(100) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }
    foo4().collect { value-> println("foo4.$value\t") }

    /*
     * 流是类似于序列的冷流 —— 流生成器中的代码在流被收集之前不会运行
     */
    fun foo5(): Flow<Int> = flow {
        println("Flow started")
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }
    println("foo5 flow collect")
    foo5().collect { value-> println("foo5.$value\t") }
    foo5().collect { value-> println("foo5.$value\t") } //会再次调用 flow {}

    /*
     * Flow cancellation
     * 在一个可取消的挂起函数(如delay)中被挂起时，flow collection 能取消。
     */
    fun foo6(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            println("foo6.Emitting $i")
            emit(i)
        }
    }
    withTimeoutOrNull(250) { // Timeout after 250ms
        foo6().collect { value -> println(value) }
    }

    /*
     * kotlinx.coroutines.flow.Builders.kt 中有些其它的 Flow 构建函数
     * asFlow()   flowOf() channelFlow()  callbackFlow()
     */
    (1..3).asFlow().collect { value -> print("asFlow().$value \t") }
    println()

    /*
     * kotlinx.coroutines.flow.Transform.kt 中定义了 filter、map 等变换函数，
     * 它们通常用于流的中间变换
     */
    suspend fun performRequest(request: Int): String {
        delay(500) // imitate long-running asynchronous work
        return "response $request"
    }
    (5..7).asFlow() // a flow of requests
        .map { request -> performRequest(request) }
        .collect { response -> println(response) }

    /*
     * 最基础的变换操作符 transform; 内部可以 多次 emit 数据，相应的 collect时就会收到多个。
     */
    (8..10).asFlow() // a flow of requests
        .transform { request ->
            emit("Making request $request")
            emit(performRequest(request))
        }
        .collect { response -> println(response) }


    /*
     * 数量大小限制操作符  take(n) 前n个； drop(n) 后n个
     */
    flowOf(1, 2, 3).take(2).collect { response -> println(response) }

    /*
     * 终结操作符，collect是最基本的，其它，如：
     *  toCollection, toList, toList;
     *  first, last;
     *  reduce, fold
     */
    val sum = (1..5).asFlow().map { it.toDouble().pow(2) }.reduce { acc, i -> //acc 保存累计值
        println("square value: $i")
        acc + i
    }
    println("sum=$sum")

    withContext(coroutineContext + Dispatchers.Default) {
        //collect是 挂起函数
        foo4().collect { value ->
            println(value) // run in the specified context
        }
    }

    /*
     * Flow 数据的 发射与处理发生在不同的 CoroutineContext 中，会报异常 IllegalStateException: Flow invariant is violated
     *  下例中 使用 withContext ，而非 flowOn(context)
     *
     * 正确做法，就是使用 flowOn 指定流执行的 context
     */
    fun foo7() = flow {
//        withContext(coroutineContext + Dispatchers.Default) {
            for (i in 1..3) {
                delay(200)
                println("${Thread.currentThread().name}")
                emit(i)
            }
//        }
    }.flowOn(Dispatchers.Default)
    foo7().collect { value -> println("foo7{ ${Thread.currentThread().name} }.$value") }

    /*
     * buffer 缓存操作，在collect的同时执行 foo8的代码，等待100ms后(把所有值缓存起来)，再每个值消费300ms，结果等于1000ms左右。
     * 如果没有buffer，就是 (100+300)*3=1200ms左右
     *
     * conflate 合并操作符。  当收集器处理中间值太慢(慢于上游的)时，使用合并操作符跳过中间值
     *      当收集器处理第1个值还未结束时，后面两个值已经发过来了；会跳过第二个中间值，直接处理最后一个；
     *      所以用时 1000-300=700ms左右。
     */
    fun foo8(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // pretend we are asynchronously waiting 100 ms
            emit(i) // emit next value
        }
    }
    val time = measureTimeMillis {
        foo8()
//            .buffer() // buffer emissions, don't wait
            .conflate()
            .collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
    }
    println("foo8.Collected in $time ms")

    /*
     * 收集和发射都较慢时，使用 collectLatest 取消并重新发送最近的一个值。
     * 对于快速的操作没有影响；对于慢速的，后一个启动时，会取消前一个没开始的；
     * 下例，打印 Done $value 时，需要先等待300ms，而发射1个值为100ms；所以 Done $value 只输出一次，value=3
     */
    val timeX = measureTimeMillis {
        foo8()
            .collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(300) // pretend we are processing it for 300 ms
                println("Done $value")
            }
    }
    println("Collected in $timeX ms")

    /*
     * 类似 Sequence.zip 。对两个 Flow 进行组合。
     */
    val nums = (1..3).asFlow() // numbers 1..3
    val strs = flowOf("one", "two", "three") // strings
    nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
        .collect { println(it) } // collect and print

    /*
     * Combine
     *
     * 重新计算combine(flow) 中参数flow的上游发射值。
     * 会输出：
     *      1 one
     *      2 one
     *      2 two
     *      3 two
     *      3 three
     */
    val numsX = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
    val strsX = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
    val startTime = System.currentTimeMillis() // remember the start time
    numsX.combine(strsX) { a, b -> "$a -> $b" } // compose a single string with "combine"
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTime} ms from start")
        }

    /*
     * flatMapConcat 操作符，将上游的流中所有发射的元素，平铺后，合并一个变换函数，返回变换后的流。类型可以变换。
     *      下例，会输出 11 22 33 。变换完一个，才继续变换下一个。
     *
     * flatMapMerge ，内部会先将 上游的 Flow，进行map变换，即调用例子中的 requestFlow(it)；
     *      整体一起去(并发性的)变换， 会输出  123 123
     *
     * flatMapLatest, 与 collectLatest 类似；
     *      对于快速的操作没有影响；对于慢速的，后一个启动时，会取消前一个没开始的；
     *      这里 requestFlow() 对应的是发射动作，最终只有最后个delay()后的反射才有效； 输出 1233
     *
     */
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // wait 500 ms
        emit("$i: Second")
    }
    val startTimeX = System.currentTimeMillis() // remember the start time
    (1..3).asFlow().onEach { value-> delay(100)
//        println(value)
    } // a number every 100 ms
//        .flatMapConcat { requestFlow(it) }
//        .flatMapMerge { requestFlow(it) }
        .flatMapLatest { requestFlow(it) }
        .collect { value -> // collect and print
            println("$value at ${System.currentTimeMillis() - startTimeX} ms from start")
        }

    /*
     * Flow exceptions
     * 任何发射、中间、终端操作 都可以捕获异常
     */
    fun fooEx(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i) // emit next value
        }
    }
    try {
//        fooEx().collect { value ->
        fooEx().map { value ->
            println(value)
            check(value <= 1) { "Collected $value" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }

    /*
     * 使用 catch {e->} ，能达到对前面的flow流操作的异常进行捕获的目的。
     * 像 collect 这样的 终结操作符 没有返回 Flow，所以不能使用 catch {}。
     * catch 是中间操作符。
     */
    fun fooEx2(): Flow<Int> = flow {
        for (i in 1..3) {
//            println("Emitting ${i/0}")
            println("Emitting ${i/1}")
            emit(i) // emit next value
        }
    }
    fooEx2().catch { e -> println("fooEx2.caught $e") }.collect { value ->
        println("$value")
//        println("$value ${3/0}")
    }

    /*
     * Catching declaratively  声明式异常处理
     * 通过 onEach 将之前的 collect中的主处理语句移动 onEach{}中，之后才 catch {}，最后只要使用 collect()。
     * 这样就能避免在 collect{}中的异常不可控，让其也被 catch{}处理。
     */
    fooEx2().onEach { value -> println("$value ${3/0}") }.catch { e -> println("fooEx2.caught $e") }.collect()

    /*
     * 使用 finally{} 在流收集完成后进行其它操作。
     */
    try {
        fooEx2().collect { value -> }
    } finally {
        //done
    }
    //在onCompletion之中，处理完成操作
    fooEx2().onCompletion {  }.collect { value -> }
    /*
     * onCompletion {} 可以对 异常进行处理，内部会再次抛出异常，后续使用 catch {} 再处理。
     * onCompletion {cause->} 可用于确定流集合是正常完成还是异常完成。
     * onCompletion {} 只会看到上游的异常，而无法看到下游的异常。
     */
    fooEx2()
        .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
        .catch { cause -> println("Caught exception") }
        .collect { value ->
            println(value)
//            check(value <=1 ) //在=2时 会抛出异常
        }

    /*
     * onEach {} 是中间操作符。配合 collect 终端操作符，会等待一个元素执行后，再执行下一个。
     *      配合 launchIn 操作符，它需要指定一个协程范围 CoroutineScope的实例；onEach 流中的元素就会在这个协程中执行。
     *      launchIn 会返回一个 Job，可以调用cancel()来取消。
     */
    fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(500) }
    events()
        .onEach { event -> println("Event: $event") }
//        .collect() // <--- Collecting the flow waits
        .launchIn(CoroutineScope(Dispatchers.IO)) //在新协程中去执行打印，控制台看不到输出。
//        .launchIn(this) //在当前 main()=runBlocking{} 这个协程中执行打印，能看到输出。会输出：Done， Event:1 2 3 。
    println("Done")

    /*
     * Flow 可以 RxJava 中的 流相互转换。见 kotlinx-coroutines-reactive for Reactive Streams,
     *  kotlinx-coroutines-reactor for Project Reactor,
     *  and kotlinx-coroutines-rx2 for RxJava2
     */

    println("runBlocking.end")
}