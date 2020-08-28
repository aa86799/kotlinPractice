package com.stone.basic.controlflow

data class Perp(var a: Int, var b: String, var c: Int)

fun main(args: Array<String>) {
    sequenceOf(11)
            .takeWhile { it > 10 }
//            .constrainOnce()
            .map { it * 10 }
            .forEach { println(it) }
    sequence {
        yield(1)
        yield(2)
        yieldAll(listOf(99, 100))
        yieldAll(listOf(101, 102))
    }.forEach { println(it) }

    val oddNumbers = sequence {
        yield(1)
        yieldAll(listOf(3, 5))
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers.take(5).toList())
//    listOf(1, 2, 3)
//            .map { it * 10 }
//            .forEach {
//                println(it)
//            }

//    listOf(1, 2, 3)
//            .flatMap {
//
//            }
//    val result = (1..10).fold(2) {
//        acc, i ->
//        acc * i
//    }
//    println(result)


    val numbers = listOf("one", "two", "three", "four", "cive", "six")

    println(numbers.dropLastWhile { it.startsWith("") })
    println(setOf(5, 7, 5, 1, 1, 1, 2, 3, 4))

    val p1 = Perp(129, "stone1", 103)
    val p2 = Perp(169, "stone2", 102)
    val (aa, bb) = Perp(199, "stone", 101)
    println("$aa $bb")

    val li = listOf(p1, p2)
    for ((aa, bb) in li) {
        println("$aa $bb")
    }

    val o = object : XC001 {
        override fun cc() {
            println("cc")
        }
    }
    o.speak()
}

@Target(AnnotationTarget.CONSTRUCTOR)
@Repeatable
annotation class ASD

annotation class Suspendable

val f = @Suspendable { 1 > 0 }

annotation class Ann
class Example(@field:Ann val foo: String,    // 标注 Java 字段
              @get:Ann val bar: String,      // 标注 Java getter
              @param:Ann val quux: String)   // 标注 Java 构造函数参数

val widget: String = ""

interface XC001 {
    fun speak() = println("11")
    fun cc()
}

class XC002 {
    @JvmSynthetic
    fun cc() {
    }
}

