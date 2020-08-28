package com.stone.basic.controlflow

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 13 48
 */

fun main(args: Array<String>) {
    /*
    while and do..while work as usual
     */

    var x = 10
    while (x > 0) {
        x--
    }

    do {
        x++
        val y = x
    } while (y < 10)
}