package com.stone.clazzobj.extension

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 19 05
 */

class MyClass {
    /*
    声明伴侣对象 ，不指定对象名，默认名为Companion
     */
    companion object {  } // will be called "Companion"
//    companion object FF{  } // will be called "Companion"
}

fun MyClass.Companion.foo() {
//fun MyClass.FF.foo() {
    println("foo")
}

fun main(args: Array<String>) {

    /*
    访问时，可以 带上 伴侣对象名 ， 或不带
     */
    MyClass.Companion.foo()
//    MyClass.FF.foo()
    MyClass.foo()
}