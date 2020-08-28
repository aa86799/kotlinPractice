package com.stone.clazzobj.extension

/**
 * desc  : 静态的扩展同名函数；
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 16 20
 */

open class C
class D: C()
fun C.foo() = "c"  //为class C 扩展 函数 foo
fun D.foo() = "d"  //为class D 扩展 函数 foo
fun printFoo(c: C) {
    println(c.foo())
}

//normal override fun
open class A {
     open fun foo() = "a"
}
class B:A() {
     override fun foo() = "b"
}
fun print(a: A) {
    println(a.foo())
}

/*
扩展函数与原类的成员，如果函数名、参数类型相同， 则原成员函数胜出
 */
class E {
    fun foo() { println("member") }
    fun fooX(arg: Int) { println("member: $arg") }
}
fun E.foo() { println("extension") }
fun E.fooX(v: Int) { println("extension: $v") }

fun main(args: Array<String>) {
    printFoo(D()) //扩展了同名函数， 并不会自动 override, 会输出 c

    println(D().foo()) //print d

    println(B()) //print b

    E().foo() //print  member
    E().fooX(3) //print  member: 3
}
