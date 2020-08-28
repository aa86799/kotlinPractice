package com.stone.coroutine

import kotlinx.coroutines.*

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/1/7 20:31
 */
fun main() = runBlocking {
    /*
     * 协同程序上下文包括一个协同程序调度器(CoroutineDispatcher，继承树中，最上级就是 CoroutineContext)，它确定相应的协同程序使用什么线程来执行。
     * 协同程序调度程序可以将协同程序的执行限制在一个特定的线程中，将它调度到一个线程池中，或者让它不受限制地运行。
     * 所有coroutine构建器(如launch和async)都接受一个可选的CoroutineContext参数，该参数可用于显式地为新的coroutine和其他上下文元素指定dispatcher。
     */
    launch { // context of the parent, main runBlocking coroutine  继承父协程的 CoroutineContext 及 Dispatcher
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread  未限制执行线程
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher  与 GlobalScope.launch()使用的 后台共享线程池是一样的。
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread  一个新的独立线程。创建新线程，这是一种昂贵的资源，尽量不使用。
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }

    /*
     * Dispatchers.Unconfined 未限制
     * 适用于既不消耗CPU时间，也不更新任何被限制在特定线程中的共享数据(如UI)的协程。
     * 第一个挂起点前，调用者线程(继承父级的context，或自身还指定了其它context)中启动协程;
     * 挂起后，由具体的挂起函数，决定后续操作(是否明显的消耗cpu时间?)在哪个线程中执行。
     *
     * 下例，挂起函数delay之前，主线程中运行；之后，由于delay 明显消耗了cpu时间，切换到 默认(后台)线程执行。
     * 当不使用 delay(..) ，只使用 挂起函数 yield()， 结果是之后的操作还是在 主线程中。
     * (该例，并不反映 yield 的实际应用场景 )
     */
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        println("a.Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
//        yield()
        println("a.Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }

    /*
     * 下例，通过 + 连接，指定了 context。+ 连接的重点是在  + 符号之后，这里直接 使用的是 父级的  coroutineContext，即主线程来执行。
     * 所以结果是，在挂起函数前后 都是 执行在 主线程中。
     * 若，改成 coroutineContext + Dispatchers.Unconfined， 那结果就和上例一样了。
     *
     * 综上，Dispatchers.Unconfined 仅在特定场景中使用。即需要立即启动协程，不操作共享数据。
     * 在编写 通用(框架)代码时，不要主动使用它。
     */
    launch(Dispatchers.Unconfined + coroutineContext) { // not confined -- will work with main thread
        println("b.Unconfined      : I'm working in thread ${Thread.currentThread().name}")
        delay(500)
        println("b.Unconfined      : After delay in thread ${Thread.currentThread().name}")
    }

    /*
     * 协程的调试
     * edit configurations, 对 VM options 添加 -Dkotlinx.coroutines.debug
     * 在输出、打印日志的语句中，打印线程名字，就能看到 该语句由哪个协程来调用。 如 @coroutine#6     #后的数据，就是第几个启动的协程。
     *
     * coroutineContext[Job] 用于查看当前协程的Job信息。
     */
    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                println("Started in ctx1. ${Thread.currentThread().name} ... ${ctx2.executor}")
                async { withContext(ctx2) {
                    println("Working in ctx2. ${Thread.currentThread().name}")
                } }
                println("Back to ctx1. ${Thread.currentThread().name}")
                println("My job is ${coroutineContext[Job]}")
            }
        }
    }

    /*
     * 父协程总是等待其所有子程序的完成。
     * 不必显式的使用 Job#join() 来等待该父协程的完成。
     * 如下例，显式使用 Job#join() 后，会阻塞所在协程
     */
    val request = launch {
        repeat(3) { i -> // launch a few children jobs
            launch  {
                delay((i + 1) * 200L) // variable delay 200ms, 400ms, 600ms
                println("Coroutine $i is done")
            }
        }
        println("request: I'm done and I don't explicitly join my children that are still active")
    }
    request.join() // wait for completion of the request, including all its children
    println("Now processing of the request is complete")

    /*
     * 像上面的例子，在调试时，协程会自动分配个名字 @coroutine#n ，n来表示第几个协程。
     * 可以通过 CoroutineName 这个 CoroutineContext 来指定 协程的name。
     * 如下，输出 "Computing v1 main @v1coroutine#14"
     */
    println("Started main coroutine")
// run two background value computations
    val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        println("Computing v1 ${Thread.currentThread().name}")
        252
    }
    val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        println("Computing v2 ${Thread.currentThread().name}")
        6
    }
    println("The answer for v1 / v2 = ${v1.await() / v2.await()}  ${Thread.currentThread().name}")

    /*
     * 梳理下 CoroutineScope CoroutineContext CoroutineDispatcher Job 。
     *
     * CoroutineScope 接口只有一个属性， 就是 coroutineContext。
     * GlobalScope 是 CoroutineScope 的单例 实现。它是全局的。不受父协程约束的。
     * CoroutineScope 有两个扩展函数 launch(): Job 和 async(): Deferred<T>
     * 通过 这两个函数 和 top 函数  runBlocking() 可以创建一个协程。 统称为 协程构建器。
     * 这三个创建(启动)协程函数，可接受 一个 CoroutineContext 参数，指定 协程的运行线程。
     *      单例类Dispatchers的四个属性，都实现了 CoroutineDispatcher，即实现了 CoroutineContext。
     *
     * 在android中，创建一个 与 Activity/Fragment 生命周期相关的 CoroutineScope 的实例，来管理协程的 生命周期。
     *  CoroutineScope() 或 MainScope() 可以创建一个CoroutineScope实例。
     * 前者，创建一个普通的/通常的 scope；
     * 后者，使用 Dispatchers.Main 作为默认的 调度器，来创建一个关于 UI 应用程序的 scope。
     *
     * class Activity {
     *  private val mainScope = MainScope()
     *  // mainScope.launch{}  mainScope.async{}
     *  fun destroy() {
     *      mainScope.cancel()
     *  }
     * }
     * 或者  class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) { //委托，赋予协程特性
     *      //launch{}  async{}
     *      fun destroy() {
     *          cancel()
     *      }
     * }
     *
     * android中，也可以引入lifecycle和协程相关的library，从而使用如 lifecycleScope, viewModelScope来创建协程。
     */

    /*
     * 使用 ThreadLocal#asContextElement(value)，来改变 在某一协程内的 ThreadLocal变量值。
     *      ThreadLocal#asContextElement(value)， 返回 ThreadContextElement ，其继承 ThreadContext。
     * 具有相同ThreadContext(包括继承自父协程的context)的协程，ThreadLocal变量值是相同的。
     */
    val threadLocal = ThreadLocal<String>()
    threadLocal.set("main")
    println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")//main
    val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
        println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")//launch
        yield()
        threadLocal.ensurePresent()
        launch(newSingleThreadContext("single") + threadLocal.asContextElement(value = "single")) {
            println("After yield2, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")//main
        }
        launch {
            threadLocal.ensurePresent() //检查当前的 threadLocal变量 在 当前的协程上下文中，若不在，会抛出异常; 强制正确使用线程局部变量与协程。
            println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'") //launch
        }
        launch {
            threadLocal.ensurePresent()
            println("After yield1, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")//launch
        }
    }
    job.join()
    println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")//main

    // 同是 Main、Default、Unconfined，才会在 yield 切换运行的协程时，在同一线程中。
    // IO 会创建新线程。 Default 内部是一个线程池。
    runBlocking {
        launch(Dispatchers.Unconfined, CoroutineStart.LAZY) {
            for (i in 0..3) {
                println("aaaa ${Thread.currentThread().name}")
                yield()
            }
        }
        launch(Dispatchers.Main, CoroutineStart.LAZY) {
            for (i in 0..3) {
                println("bbbb  ${Thread.currentThread().name}")
                yield()
            }
        }
    }

    println()

}