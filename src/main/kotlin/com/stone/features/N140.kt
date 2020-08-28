package com.stone.features

import kotlin.properties.Delegates

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/28 00:53
 */
interface Action {
    fun run()
}

fun interface Action2 {
    fun run()
}

//参数为 Kotlin 单一方法接口
fun runAction(a: Action) = a.run()
fun runActionX(a: Action2) = a.run()

//参数为 Java 单一方法接口
fun runRunnable(r: Runnable) = r.run()

fun main() {
    runAction(object : Action {
        //before 1.4
        override fun run() {
            println("is this Action = ${this is Action}")
        }
    })

    runActionX(Action2 { //Action2 都可以省略
//        println("${this}") //this 不能调用
        println("Action2-1")
    })

    runActionX {
//        println("${this}") //this 不能调用
        println("Action2-2")
    }

    /* --------------------------- */
    val result = run {//推导出永不为null，一定是非null型
        var str: String? = null
        if (str == null) {
            str = "test"
        }
        str
    }

    /* --------------------------- */
    //有默认参数的函数，被其它函数接收时，可以被 使用参数或不使用参数的函数接收。除此之外，该函数还是表示带参函数的类型
    fun foo(i: Int = 0): String = "$i!"
    fun apply1(func: () -> String): String = func()
    fun apply2(func: (Int) -> String): String = func(42)
    println(apply1(::foo))
    println(apply2(::foo))

    /* --------------------------- */
    //属性的代理推断中，对可空类型，可以赋值  非空或null
    var prop: String? by Delegates.observable(null) { p, old, new ->
        println("${p.name} : $old → $new")
    }
    prop = "abc"
    println(prop)
    prop = "xyz"
    println(prop)

    /* --------------------------- */
    fun f(a: Int = 2, b: Int, c: Int) {}
    f(1, 2, 3)
//    f(2,3) //默认在最前面，非具名时，还是不能省略掉。 这里赋值的还是 a 和 b。
    f(b = 3, a = 3, c = 4)// 具名，可以乱序
    f(b = 3, c = 4)// 具名，可以省略a

    /* --------------------------- */
    //在类的属性声明和属性值传入时，允许最后一行加逗号
    data class Per(
        val a: Int,
        val b: Int,
        val c: Int,
    )
    Per(1, 2, 3)
    Per(
        2,
        4,
        6,
    )

    /* --------------------------- */
    //循环中的 when {} 可以使用 continue 和 break 了。 但像 foreach 这种操作符中是不行的
    for (it in 0..100) {
        when {
            it % 2 == 0 || it % 3 == 0 -> continue
            it > 50 -> break
            else -> print("$it ")
        }
    }
    println()


    /* --------------------------- */
    //新增集合操作符
    val set = setOfNotNull(null, 1,2,null) //只保留非空的

    //shuffled() for sequences.
    val numbers = (0 until 50).asSequence()
    val resultS = numbers.map { it * 2 }.shuffled().take(5)

    //
    "abc".toList()
    listOf("a", "b", "c", "d").onEachIndexed {
            index, item -> println("$index:$item")
    }
    val list = listOf("hello", "kot", "lin", "world")
    val kotlin = list.flatMapIndexed { index, item ->
        if (index in 1..2) item.toList() else emptyList()
    }

    //randomOrNull(), reduceOrNull(), and reduceIndexedOrNull(). They return null on empty collections.
    val empty = emptyList<Int>()
    val resultE = empty.reduceOrNull { a, b -> a + b }

    //runningFold()、scan()、runningReduce() 会返回中间结果集； reduce()、fold()等是 直接返回最终结果
    val numbers2 = mutableListOf(0, 1, 2, 3, 4, 5)
    val runningReduceSum = numbers2.runningReduce { sum, item -> sum + item }
    println(runningReduceSum)
    val runningFoldSum = numbers2.runningFold(10) { sum, item -> sum + item }
    println(runningFoldSum) //先出输出一次sum，再继续操作；比 reduce {} 多一个输出
    val reduce = numbers2.reduce { acc, i -> acc + i }
    println(reduce)

    //sumOf{} 返回函数值的累加和
    data class OrderItem(val name:String, val price: Double, val count: Int)
    val order = listOf<OrderItem>(
        OrderItem("Cake", price = 10.0, count = 1),
        OrderItem("Coffee", price = 2.5, count = 3),
        OrderItem("Tea", price = 1.5, count = 2))
    val total = order.sumOf { it.price * it.count} // Double
    val count = order.sumOf { it.count } // Int
    println("count=${count}, total=$total")

    //min、max ..OrNull()，返回可null。 maxOf()、minOf() 返回非null
    val max = order.maxOfOrNull { it.price }
    val highestPrice = order.maxOf { it.price }
    val lowestPrice = order.minOf { it.price }
    order.minWithOrNull { a, b ->
        if (a.price > b.price) 1
        else if (a.price < b.price) -1
        else 0
    }

    //flatMap, flatMapTo  可操作 Sequence
    val list2 = listOf("kot", "lin")
    val lettersList = list2.flatMap { it.asSequence() } //[k, o, t, l, i, n]
    val lettersSeq = list2.asSequence().flatMap { it.toList() } //[k, o, t, l, i, n]

    //Array 的新函数：shuffle, onEach, reverse, sort, sortDescending, sortWith
    arrayOf(1,2,).contentToString()
    arrayOf(1,2,).sortDescending()
    arrayOf(1,2,).associateWith { it * 10 }.onEach { println("${it.key} to ${it.value}") }

    //
    println("------ArrayDeque 双端队列--------")
    val deque = ArrayDeque(listOf(1, 2, 3))
    deque.addFirst(0)
    deque.addLast(4)
    println(deque) // [0, 1, 2, 3, 4]
    println(deque.first()) // 0
    println(deque.last()) // 4
    deque.removeFirst()
    deque.removeLast()
    println(deque) // [1, 2, 3]

    //StringBuilder 函数新增
    val sb = StringBuilder("Bye Kotlin 1.3.72")
    sb.deleteRange(0, 3)
    sb.insertRange(0, "Hello", 0 ,5)
    sb.set(15, '4')
    sb.setRange(17, 19, "0")
    println(sb.toString())

    //Bit Manipulations
    val number = "10100100".toInt(radix = 2) //以二进制转成 Int
    println(number.countOneBits()) //二进制中 1位的 个数, out 3
    println(number.countTrailingZeroBits()) //最右边的0位 个数,  out 2
    println(number.takeHighestOneBit().toString(2)) //最高位的1位起，后面为0; 然后 转成 二进制 字符串。 out 1000 0000
    println(number.takeLowestOneBit().toString(2)) //最低或最右的 1位起，后面为0; 然后 转成 二进制 字符串。out 100

}