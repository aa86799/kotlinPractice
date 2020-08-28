package com.stone.clazzobj.extension

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 16 10
 */

/*
扩展一个类的函数， 声明时， 在函数名前 加上 " classType. "
 */
//扩展 MutableList<Int>类，增加一个函数名为swap
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

//this function makes sense for any MutableList<T> , and we can make it generic
fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

class TXY {
    fun MutableList<Int>.swap22(index1: Int, index2: Int) {

    }

    fun test() {
        val list = mutableListOf(1, 3, 2)
        list.swap22(1, 2)
    }
}
fun main(args: Array<String>) {
    val list = mutableListOf(1, 3, 2)
    list.swap(1, 2)
//    list.swap22(1, 2)// 扩展函数即使在一个公共类中，也不能影响全局，只能类的内部使用
    list.forEach(::println)
}