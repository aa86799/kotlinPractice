package com.stone.basic.controlflow


/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 13 58
 */

fun breakContinueReturn() {
    var x = 10
    while (x > 0) {
        x--
        if (x == 2) {
            break
        }
    }

    do {
        x++
        val y = x
        if (y == 4) {
            continue
        }
        if (y == 5) {
            println(y)
            return
        }
    } while (y < 10)

    println("do sth.")
}

//使用标签 label@ 限制 break或continue、return的跳转
fun labelJump() {
    loop@ for (i in 1..3) {
        inner@ for (j in 1..3) {
            if (i == 1 && j == 3) {
                continue@inner
            }
            if (i == 2 && j == 2) {
                break@loop
            }
            println("$i, $j")
        }
    }
    (1..3).forEach jump@ { item ->
        run {
            if (item == 1) {
                return@jump
            }
            println("-" + item)
        }
    }

    (1..3).forEach { item ->
        run {
            if (item == 1) {
                return@forEach //lambda函数 的标签名与 函数名一致  可以不用像上面那样自定义
            }
            println("-" + item)
        }
    }

    var x = 3
    aa@ for (i in 1..x) {
        b@ for (j in 1..4) {
            if (i == 2 && j == 1) {
                break@aa
            }
        }
    }
}

//使用 匿名函数替换lambda表达式，且使用return后，return的只是该匿名函数
fun anonymousFun() {
    var list = listOf<Int>(3, 4, 5)

    list.forEach(
            fun(value: Int) {
                if (value == 5) return
                println(value)
            }
    )
}

fun main(args: Array<String>) {

    labelJump()

    anonymousFun()
}