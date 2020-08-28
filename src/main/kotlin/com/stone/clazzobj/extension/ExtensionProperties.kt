package com.stone.clazzobj.extension

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 17 54
 */
//扩展List<T> 属性， 名为lastIndex ，提供了get()
val <T> List<T>.lastIndex: Int get() = size - 1

class Foo(bar: Int) {
    private val barV: Int = bar
    var barX: Int = -1
        get() {
            if (field > 0) return field
            else return barV
        }
        set(value) { field = value }
}
//val Foo.bar = 1 //扩展属性，并不会插入成员到实际中的class中。扩展属性中不具有 backing field。 所以需要明确的声明 get/set
val Foo.bar2 get()  = this.barX
val Foo.bar3: Int get()  = 10

fun main(args: Array<String>) {
    val list = listOf(1, 2)
    println(list.lastIndex)

    val foo = Foo(7)
    val v = foo.bar2
    println(v)

    foo.barX = -3
    println(foo.barX)

    foo.barX = 33
    println(foo.barX)
}
