package com.stone.fuctions

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 28/07/2017 13 22
 */

/*
一个函数声明中，将另一个函数作为参数或返回值，这样的函数就是高阶函数(Higher-Order Functions)
 */

fun <T> lock(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}

//使用方法引用
fun <T> usFun(a: Int, body: (Int) -> T): T {//body中，也可声明成 (Int) -> T
    return body(a)
}
fun suffix(arg: Int): String {
    return "${arg}_suffix"
}
val result =  usFun(3, body = { arg -> suffix(arg) })
val resultX =  usFun(4) {arg -> suffix(arg)}
val resultXX = usFun(5, ::suffix)

/*
仿_Collections.kt中的map函数
 */
fun <T, R> List<T>.mapz(transform: (T) -> R): List<R> {
    val result = arrayListOf<R>()
    for (item in this)
        result.add(transform(item))
    return result
}

/*
使用函数表达式做为 函数型 形参；也可以使用方法引用 来调用
 */
fun compare(a: String, b: String): Boolean = a.length < b.length
fun maxLen(a: String, b: String, op: (String, String) -> (Boolean)): String {
    if (op(a, b)) {
        return b
    } else {
        return a
    }
}

/*
接收字面值 函数，
以下，调用： Int.sum(other-Int)
 */
val sum = fun Int.(other: Int): Int = this + other

/*
接收函数，
HTML().init() 接收一个 ()->Unit 函数
 */
fun cc() {}
class HTML {
    fun body() { }
}
fun html(init: HTML.() -> Unit): HTML {
    val html = HTML() // create the receiver object
    html.init() // pass the receiver object to the lambda
    return html
}

fun main(args: Array<String>) {
    lock(ReentrantLock(), body = {})
    lock(ReentrantLock()) {}


    println()
    println(result)
    println(resultX)
    println(resultXX)

    println()
    listOf(1, 2).mapz { i -> i * 2f }.forEach { println(it) }

    println()
//    val maxLenStr = maxLen("abcddd", "aaa") { s1, s2 -> compare(s1, s2) }
//    val maxLenStr = maxLen("abcddd", "aaa", ::compare)
    val comp: (String, String) -> Boolean = ::compare
    val maxLenStr = maxLen("abcddd", "aaa", comp) //声明一个 类型为函数形的属性
    println(maxLenStr)

//    val vv = println("aa") //声明一个 类型为函数形的属性
    val vv: (Any?) -> Unit = ::println //声明一个 类型为函数形的属性
    fun aa() = vv
    aa()

    println()
    listOf(3, 4).filter { it > 0 }
    listOf(3, 4).filter(fun(item) = item > 0)

    println()
    1.sum(3)

    html { // lambda with receiver begins here
        body() // calling a method on the receiver object
    }

    html {
        HTML().body()
    }
    html {
        cc()
    }
}