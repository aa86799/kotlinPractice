package com.stone.basic.types

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 30/05/2017 20 48
 */
fun main(args: Array<String>) {
    /* 使用三个双引号开头与结尾， 中间可以包含 任何 非转义字符 */
    val text = """
|Tell me and I forget.
|Teach me and I remember.
|Involve me and I learn.
|(Benjamin Franklin)
>admin""".trim().trimMargin().trimMargin(">")  //trimMargin 去掉前缀，默认以|作margin前缀，也可以指定前缀
    println(text)

    var s = "abc"
    var str = "$s.length is ${s.length}"

    val price = """${'$'}9.99${"\t"}一杯果汁"""
    println(price)
}