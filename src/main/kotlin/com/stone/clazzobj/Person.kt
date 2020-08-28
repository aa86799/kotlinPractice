package com.stone.clazzobj

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 30/05/2017 15 32
 */

open class Human {
    val id: Int
    val name: String
    constructor(id: Int, name: String) {//构造方法
        this.id = id
        this.name = name
    }
}
class Person(id: Int, name: String, nickname: String) : Human(id, name) {
    fun talk() {}
    fun HBB() {// protected不能用顶级函数声明

    }
}

fun main(args: Array<String>) {
    val person = Human(0x11, "")
    val person2: Person = Human(0x11, "") as Person //调用父类构造方法 as 成 子类
    val person3 = Person(0x11, "", "nickname") //实例化Person类
}

