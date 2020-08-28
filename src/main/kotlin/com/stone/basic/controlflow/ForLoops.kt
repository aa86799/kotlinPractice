package com.stone.basic.controlflow

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 11 12
 */
fun main(args: Array<String>) {

    for (item in listOf<Int>()) print(item) //in Collection

    for (item: Float in arrayOf<Float>()) { //in Array , item带上类型
    }

    /*
    使用for 迭代的对象， 都需要通过一个 iterator. 其内部：
    有一个成员或扩展函数，名为iterator(): X ；  且其返回类型中
        有一个成员或扩展函数 next(): T
        有一个成员或扩展函数 hasNext(): Boolean
     */

    var list = listOf<Int>(1, 2)
    for (i in list.indices) {
    }
    var ary = arrayOf(3, 4)
    for (i in ary.indices) {
    }

    for ((index, value) in ary.withIndex()) {//winIndex 返回 Iterable<IndexedValue<T>>
        println("the element at $index is $value")
    }
}