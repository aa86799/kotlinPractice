package com.stone.features


/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 13/06/2017 10 00
 */
object RangeAction {

    fun range1() {
        for (x in 1..5) {//include 5
            print("stone--range1-->$x\t")
        }
        println()
    }

    fun range2() {
        for (x in 0..10 step 2) {//step2 ==> x+=2
            print("stone--range2-->$x\t")
        }
        println()
    }

    fun range3() {
        for (x in 0 until 10 step 2) {//until 10 ==> not include 10
            print("stone--range3-->$x\t")
        }
        println()
    }

    fun range4() {
        for (x in 10 downTo 0 step 2) {//downTo 0 ==> >=0
            print("stone--range4-->$x\t")
        }
        println()
    }

    fun range5() {
        val range = (10 downTo 0 step 2)
        println("stone--range5-->${range.first}\t${range.last}\t${range.step}\t${range.isEmpty()}")
        for (x in range.step(3)) {//step(set:Int) ==> change source step

        }

        val check = range.all { it  >= 0 } //all match fun expression
        println("stone--range5-->${range.step(3)}》$check ${range.maxOrNull()} ${range.count()}")

        /*
        range type is IntProgression
        other type : CharProgression  LongProgression
         */
        print("stone--range5-->")
        for (c in 'a'..'z') {
            print("$c ")
        }
        println()

        for (x in 0L until 10L) {

        }
    }

    fun range6() {
        var range = (0 until 10 step 2)

        print("stone--range6-->")
        //Spliterator (java8) 分割迭代  递归的将集合 分割成更小的集合 进行迭代， 当不可再分时 返回null
        range.spliterator().trySplit().forEachRemaining { print("$it ") } //"it" refer to the element of collection
        println()

        print("stone--range6-->reverse-->")
        range = range.reversed()
        range.filter { i -> i > 2 }.map { it / 1.8 }.forEach({ print(String.format("%.2f ", it)) })
        println()

//        range.forEach(::println) //method reference
    }

    fun range7() {
        var range1:IntRange = 1.rangeTo(110)
        var range2:LongRange = 1L.rangeTo(110L)
        var range3 = 10.downTo(1)
        var range4 = 10.until(20)
    }
}

fun main(args: Array<String>) {
    RangeAction.range1()
    RangeAction.range2()
    RangeAction.range3()
    RangeAction.range4()
    RangeAction.range5()
    RangeAction.range6()
}
