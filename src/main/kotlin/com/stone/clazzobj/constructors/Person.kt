package com.stone.clazzobj.constructors

//import kotlin.reflect.jvm.internal.impl.javax.inject.Inject

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 15 44
 */
class Person1 constructor(firstName: String) {}//主构造函数可以直接定义在类名后
class Person2 constructor(val firstName: String) { }
class Person3 constructor(var firstName: String) { }
class Person4(firstName: String) { } //如果主构造函数没有任何注解或可见性修饰符,构造函数关键字可以省略

class Person5(name: String) {//若主构造函数中，不进行初始化, 可放在init{}中
    val name: String
    init {
        println("initialize")
        this.name = name
    }
}

class Person6(name: String) {//主构造函数中的参数初始化，也可以用在成员属性上
    val name = String
//    val name: String = name
}

//如果主构造函数前有可见性修饰符或注解，就一定需要加上constructor 关键字
class Person7 private/* @Inject*/ constructor(name:String, age: Int)

/*
当有其它构造函数，依赖主构造函数，形如 constructor(...) : this(...)
对于 主构造函数中的参数，如上所说，要么放在init{} ，要么以赋值给成员属性
对于 非主构造函数中的参数，在定义属性时，则就需要一个默认值了
注意：init{} 要定义在 属性定义后， 否则会访问不到
 */
class Person8(val name: String) {
    var pName: String = name
    var pAge: Int = 0 //默认值
    init {
        pName = name
    }
    constructor(name: String, age: Int) : this(name) {
        pAge = age
    }
}

//其实所有主构造函数中的参数，都会被自动赋上一个默认值， 所以也可以手动指定一个默认值
private class Person9(val name: String = "")

/*
class 中可以有：
— Constructors and initializer blocks
— Functions
— Properties
— Nested and Inner Classes
— Object Declarations
 */

fun main(args: Array<String>) {
    val per9 = Person9("stone")

    TT(3)
    TT(3, 4)
}
class TT {//没有在类名后定义主构造函数
    constructor(a: Int)
    constructor(a: Int, b:Int) : this(a)
}