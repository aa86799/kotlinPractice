package com.stone.clazzobj.generic

import java.util.*

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 05/06/2017 19 29
 */

class Box<T>(t: T) {
    var value = t
}

fun <T> get(t: T): T {//直接在top级函数中使用泛型  这里的泛型不能 定义为 in 或 out
    return t
}

class Box1<in T> {//in 表示 T 是用于 consumer的， 即 参数 类型上
    fun addAll(to: MutableCollection<Any>, from: Collection<T>) {
        from.forEach { to.add(it!!) }
    }

    fun copy(from: Array<out Any>, to: Array<Any>) {
        for (index in from.indices) {
            to[index] = from[index]
//        from[index] = to[index] //out 只能使用 get-producer 一类的方法 不能用set-consumer
        }
    }

}

abstract class Box2<out T> {//out 表示 T 是用于 producer的，即 用于 return 的类型
   abstract fun nextT(): T
}


fun copy2(from: Array<in Any>, to: Array<Any>) {
    for (index in from.indices) {
//        to[index] = from[index]
        from[index] = to[index] //in 能用set-consumer
    }
}


open class Sup
class Chi : Sup()
fun fill(dest: Array<in Chi>, value: Chi) {// ? super Chi
    val newDest = dest.plus(value)
    for (item in newDest) {
        println(item)
    }
}

fun <T> copy(from: List<out T>, to: MutableList<in T>) {
    from.forEach { to.add(it) }
}

fun printAny(list: MutableList<*>) {
    for (i in list.indices) {
        println(list[i])
    }
}

interface MyFun<in T, out U> {
    fun testp(t: T): U
    fun inParam(t: T)
    fun outValue(): U
}

fun test11(m: MyFun<*, String>) {//in 声明成*, 表示 in Nothing  即不能写入任何东西，因为此时不知道*是什么类型的
//    m.testp()
//    m.inParam()
    m.outValue()
}
fun test12(m: MyFun<Int, *>) {//out 声明成*，表示 out U
    val result = m.testp(33)
    println("test12: " + result)
    m.inParam(34)
    m.outValue()
}
fun test13(m: MyFun<*, *>) {// <in Nothing, out U>
//    m.testp()
//    m.inParam()
    m.outValue()
}
/*
当不知道in的类型时，用 *表示 in类型， 可以安全地 防止 写入
 */


fun <T> T.basicLog() { // generic extension function
    println("注入了一个扩展函数")
}

interface MyInter1<in K> {
    fun compareTo(k: K): Int
}
interface MyInter2<G> {
    fun fuck(g: G): G
}
interface MyInterClz<T>: MyInter1<T>, MyInter2<T>

fun <T, X> cloneWhenGreater(list: List<T>, x: X): List<X>
    where T: MyInter1<X>, T: MyInter2<X> {
//    return list.filter { it is MyInter1<*> }.map { it.fuck(x) }
    return list.filter { it.compareTo(x) > 0 }.map { it.fuck(x) }
}

fun main(args: Array<String>) {
//    val box: Box<Int> = Box<Int>(1)
    val box = Box(1)

    Box1<Any>().addAll(mutableListOf(), listOf("stone"))

    val vs = arrayOf(Sup()) // ? super Chi  ==> Sup
    fill(vs, Chi())
//    fill(vs, Sup())

    val list = mutableListOf(ASA(), BSB())
    Tes().covariant(list)

    val list2 = mutableListOf<ASA>()
    Tes().contravariant(list2, ASA(), BSB())

    copy(list, list2)

    printAny(list)
    printAny(list2)

    val mfun = object : MyFun<Int, String> {
        override fun testp(t: Int): String {
            return t.toString()
        }
        override fun inParam(t: Int) {
            println("mfun: $t" )
        }
        override fun outValue(): String {
            println("outValue.")
            return "MyFun"
        }
    }
    test11(mfun)
    test12(mfun)
    test13(mfun)

    mfun.basicLog()

    val sl = Collections.singletonList<Int>(3)

//    val inter = object : MyInter1<String>, MyInter2<String> {
//        override fun compareTo(k: String): Int {
//            return "MyInter1".compareTo(k)
//        }
//
//        override fun fuck(g: String): String {
//            return g + " Suffix"
//        }
//    }
//    val interList1 = listOf(inter)  //inter的类型 还是模糊的

    val myInterClz = object : MyInterClz<String> {
        override fun compareTo(k: String): Int {
            print("compare-value ==> " + ("a".compareTo(k)))
            /*
            跟java 中 string.compareTo() 不一样
            比较返回是 源在目标的 前/后 多少位： 前为负值，后为正值
            ASCII码表，小写字母是在大写字母后的
             */
            return "MyInter1".compareTo(k)
        }

        override fun fuck(g: String): String {
            return g + " Suffix"
        }
    }
    val ml:List<MyInterClz<String>> = listOf(myInterClz)
    val rList = cloneWhenGreater(ml, "Z")
    rList.forEach { print(it + "\t") }
}

//fun <T> singletonList(item: T): List<T> { // ...
//
//}

//fun cccx() {
//    val l = singletonList(1)
//}

fun <T: Any?> sdfs():T? {
    return null
}
