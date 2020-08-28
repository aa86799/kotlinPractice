package com.stone.basic.controlflow

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 10 19
 */
object IfExpression {
    @JvmStatic fun main(args: Array<String>) {
        val a = 3; val b = 6
        // Traditional usage
        var max = a
        if (a < b) max = b

        //with else
        if (a > b) {
            max = a
        } else {
            max = b
        }

        if (a > b) {
            max = a
        } else if (a < b) {
            max = b
        }

        if (a > b) {
            max = a
        } else if (a < b) {
            max = b
        } else {
            max = a
        }

        // As expression
        max = if (a > b) a else b

        //作为表达式，并使用语句块。最后一行表达式 表示值
        max = if (a > b) {
            print("Choose a")
            a
        } else {
            print("Choose b")
            b
        }


    }
}