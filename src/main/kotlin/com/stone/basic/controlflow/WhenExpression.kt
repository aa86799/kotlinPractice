package com.stone.basic.controlflow


/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 10 30
 */

//函数表达式中使用 when表达式
fun hasPrefix(x: Any) = when(x) {
    is String -> x.startsWith("prefix") else -> false
}


fun main(args: Array<String>) {
    var x = 10

    when (x) {
        1 -> print("x == 1") //case 1
        2 -> print("x == 2") //case 2
        else -> { // Note the block
            print("x is neither 1 nor 2") }
    }

    when (x) {//case 0, 1
        0, 1 -> print("x == 0 or x == 1") else -> print("otherwise")
    }

    //case 部分 可以作用 任意表达式
    var s = "9527"
    when (x) {
        Integer.parseInt(s) -> print("s encodes x") else -> print("s does not encode x")
    }

    //case 中 使用 范围类型 IntRange
    val validNumbers = IntRange(11, 15)
    when (x) {
        in 1..10 -> print("x is in the range")
        in validNumbers -> print("x is valid")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }

    //case中使用表达式，可以替换 if表达式
    val y = IntNumber(112)
    when {
        y.isOdd() -> print("x is odd")
        y.isEven() -> print("x is even")
        else -> print("x is funny")
    }
}

class IntNumber {
    private val num:Int

    constructor(num: Int) {
        this.num = num
    }

    fun isOdd(): Boolean {
        return num % 2 == 1
    }

    fun isEven(): Boolean {
        return num % 2 == 0
    }
}