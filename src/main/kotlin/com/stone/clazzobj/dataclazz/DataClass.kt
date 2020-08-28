package com.stone.clazzobj.dataclazz

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 21 12
 */
/*
data class ：所有声明在主构造函数中的属性都会派生出相应的get/set(set需要属性为var)
    还会自动生成：
        equals() / hashCode()
        toString() of the form "User(name=John, age=42)" ,
        copy() ，比 Human类的默认实现就是：
            fun copy(id: Int = this.id) = Human(id)

 */
open class Human(id: Int) {
    override fun toString(): String {
        return "Human"
    }
}
/*
data class 声明时，前面不能有 abstract, open, sealed or inner
如果类中实现或重写了某个函数后，那这个函数就不会再自动生成了
必须声明主构造函数，且至少有一个参数
 */
//data class DataTest
data class User(val id: Int, val name: String, var age: Int): Human(id) {
    var weight: Float = 0.0f
    constructor(id: Int, weight: Float) : this(id, "", 18) {
        this.weight = weight

        fun cc() {}//何用？
    }

//    override fun toString(): String {
//        return "User"
//    }
}

fun main(args: Array<String>) {
    val u = User(1, "stone", 28)
    u.age = 27
    println(u.toString())

    val (userId, userName, userAge) = u //将对象u，依其主构造函数中的参数顺序，赋值给 声明的参数
    println("userId=$userId, userName=$userName, userAge=$userAge")


    /*
    自动生成的copy()，参数上会给定默认值，所以如下，少传或不传参数
     */
    u.copy()
    u.copy(2)
    u.copy(2, "stone2")
    u.copy(2, "stone2", 27)
    u.copy(2, "stone2", 27)

    /*
    kotlin中 两个标准的 data class: Pair 和 Triple
     */
    val pair = Pair(1, "b") //任意两种类型
    val triple = Triple(1, "b", 3.3f) //任意三种类型
}