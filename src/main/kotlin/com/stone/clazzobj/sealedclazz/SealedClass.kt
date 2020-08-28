package com.stone.clazzobj.sealedclazz

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 22 01
 */
/*
密封类 : 构造函数私有 子类，不能外问直接初始化 该密封父类 (可以用于，函数参数)
 其子类必须与 密封类在同一个文件中(在1.1版本之前，子类还要求是其 内部类)
sealed 不适用于 修饰在接口和抽象类前
它的<init> 即构造函数是private的
 */
sealed class Expr {
//    public constructor() //只能是private的, 默认不写也是private
//    public constructor(a:Int) {}

    var str: String = ""

    constructor()
    constructor(a: String) {
        this.str = a
    }

    fun test() {

    }
}
//sealed class Expr(int: Int) {}

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANumber : Expr()
//class NotANumber : Expr()

fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    NotANumber -> {
        println("非double: " + Double.NaN)
        Double.NaN } //这里省去了else ，跟这是一个单例对象有关；
    is Sum -> eval(expr.e1) + eval(expr.e2)
//    else -> {
//        println("非double: " + Double.NaN)
//        Double.NaN
//    }
}

fun main(args: Array<String>) {
//    val expr = Expr() //cannot be instantiated
    val a = eval(Const(3.3))
    println(a)

    val b = eval(Sum(Const(1.2), Const(2.3)))
    println(b)

    eval(NotANumber)
}