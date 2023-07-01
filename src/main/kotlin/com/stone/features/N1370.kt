package com.stone.features

import kotlin.reflect.cast
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * desc:  1.3.70 新增实验性 api
 *      https://blog.jetbrains.com/kotlin/2020/03/kotlin-1-3-70-released/#standard-library-changes
 *      部分已在 1.4 转正
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/3/9 13:05
 */


@ExperimentalTime
//@ExperimentalStdlibApi //实验性 标准库的 api
fun main() {

    /*
     * 1.3.70 实验性注解
     * @OptIn and @RequiresOptIn have replaced @UseExperimental and @Experimental
     * 旧的声明@Experimental和@UseExperimental在1.3.70中仍然受到支持，但在1.4中将被删除
     */

    testClass()
    testArrayDeque()
    testTime()
    testBuildCollection()
    testReduce()
    testScan()
}

//@ExperimentalStdlibApi
fun testClass() {
    val kClass = String::class
    println(kClass.simpleName) // String
    println(kClass.qualifiedName) // kotlin.String
    println(kClass.isInstance("")) // true
    println(kClass.isInstance("abc")) // true
    println(kClass.isInstance(10)) // false
    println(kClass.cast("abc")) // abc
//    println(kClass.cast(1)) // error.  Value cannot be cast to kotlin.String
}

//@ExperimentalStdlibApi //实验性 标准库的 api
fun testArrayDeque() {
    /*
     * 双端队列的 kotlin实现。在需要使用队列、栈的场景下使用。
     * Kotlin的ArrayDeque实现MutableList。 这意味着您可以按索引访问所有元素，而Java.util.ArrayDeque无法实现。
     *
     */
    val deque = ArrayDeque(listOf(7, 1, 9, 18, 15, 23, 4, 5))
    deque.add(76)
    deque.addFirst(36)
    deque.addLast(28)
    println(deque)
    println(deque.first())
    println(deque.last())
    println(deque[2])
    deque.removeFirst()
    deque.removeLast()
    deque.removeFirstOrNull()
    deque.removeLastOrNull()
    println(deque)
}

@ExperimentalTime //实验性 时间 api
fun testTime() {
    /*
     * 旧有类名被重命名
     * typealias Clock = TimeSource
     * typealias MonoClock = Monotonic
     * typealias ClockMark = TimeMark
     *
     * func: representing Duration values and measuring time intervals.
     */
    /*
     * TimeSource 时间源
     * Monotonic (adj. 单调的；无变化的；产生单音调的)
     * markNow(): TimeMark
     * elapsedNow() 到现在的运行时间。调用一次就会被标记一次
     *      如下测试，结果是两次间隔时间非常短
     */
    println(TimeSource.Monotonic.markNow().elapsedNow().toDouble(DurationUnit.SECONDS))
}

@OptIn(ExperimentalStdlibApi::class)
fun testBuildCollection() {
    /*
     * buildList、buildSet、buildMap 构建新的集合
     * {} 可以调用集合的相关操作；结果会合并成一个新的只读集合
     */
    val initial = listOf(2, 6, 41)
    val ints = buildList { // this: MutableList
        add(0)
        initial.mapTo(this) { it + 1 }
    }
    println(ints) // [0, 3, 7, 42]

    val ints2 = buildList { // this: MutableList
        add(5)
        ints.mapTo(this) { it + 1 } //ints2: [5, 1, 4, 8, 43]
        remove(4)
    }
    println(ints2) // [5, 1, 8, 43]
}

//@OptIn(ExperimentalStdlibApi::class)
fun testReduce() {
    val list = listOf(1, 2, 3, 4, 5)
    println(list.randomOrNull()) //随机取一个元素，无元素返回null
    println(list.reduceOrNull { a, b -> a + b }) //所有元素进行归并。满足函数表达式。a+b就是累加，1加到5=15

    val emptyList = emptyList<Int>()
    println(emptyList.randomOrNull()) // null
    println(emptyList.reduceOrNull { a, b -> a + b }) // null

    //以前只有  random()、reduce()，在操作空集合时，会抛出一个异常
}

fun testScan() {
    /*
     * scan（）与fold（）紧密相关。 scan（）和fold（）都将给定的二元运算应用于值序列，
     * 但是不同之处在于scan（）返回整体的结果序列，而fold（）仅返回最终结果。
     * 它们可以被 List、Set 集合直接使用，不必非得是 Sequence。
     *
     * 不需要添加一个初始值时，可以使用 reduce() 代替 fold() ， scanReduce() 代替 scan()
     *
     * 如果需要 索引下标参与操作，有：
     *   reduceIndex()
     *   scanIndex()
     *   foldIndex()
     *   scanReduceIndex()
     *
     * scan 为首的函数， 是实验性的。
     */
    val ints = (1..4).asSequence()
    println(ints.fold(2) { acc, elem -> acc + elem }) // 2作为初始的acc。结果=12    fold: 折叠

    val sequence = ints.scan(0) { acc, elem -> acc + elem }
    println(sequence.toList()) // [0, 1, 3, 6, 10]
}