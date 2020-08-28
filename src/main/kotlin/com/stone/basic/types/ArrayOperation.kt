package com.stone.basic.types

import java.util.*

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 30/05/2017 20 48
 */



fun main(args: Array<String>) {

    val ary = arrayOf(1, 3, 2) //使用arrayOf 创建数组

//    val asc = Array(5, { i -> (i * i).toString() })
    val asc = Array(5, { i -> Math.random() }) //使用构造函数创建数组：后面的lambda参数，表示设置每个index上的元素
    for (it in asc) {
//        println(it == "1")
        println(it)
    }

    val ary2 = arrayOfNulls<Long>(2) //每个数组元素中 填充一个null值
    for (i in ary2.indices) {//indices 返回一个 索引范围 : IntRange
        ary2[i] = 3L + Random().nextInt(10)
        println("ary2[$i] : ${ary2[i]}") //[] 可以用于 get 和 set 操作
    }

    val ary3 = doubleArrayOf(1.0, 2.2, 3.3) //基本数据类型都对应有一个 xxArrayOf函数
}