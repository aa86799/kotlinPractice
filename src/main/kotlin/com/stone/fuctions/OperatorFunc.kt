package com.stone.fuctions

import java.text.SimpleDateFormat
import java.util.Calendar

class OperatorFunc(private val tag: String) {

    operator fun invoke() {
        println("$tag")
    }

    operator fun invoke(name: String) {
        println("$tag $name")
    }

    operator fun invoke(name: String, time: String) {
        println("$tag $name, $time")
    }
}

fun testCallOperatorFunc(func: OperatorFunc) {
    // 先执行 func() -> call invoke()
    println("testCallOperatorFunc： ${func()}")
}

fun main() {
    /*
     * operator fun invoke(...)  对象运行符函数；
     * 调用 invoke(...) 时，仅需 对象(...)  即可， 可以省略 invoke 函数名。
     *
     * 注意：如下示例中， func 仅代表对象， func(...) 才能代表 invoke(...)函数
     */
    val func = OperatorFunc("Hello")
    func() // call invoke(), print : Hello
    func("World") // call invoke(name), print : Hello World

    println("----")
    testCallOperatorFunc(func)

    println("--------")
    func("Tom", SimpleDateFormat().format(Calendar.getInstance().time))
}