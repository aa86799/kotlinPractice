package com.stone.clazzobj.interfaces

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 14 03
 */
/*
接口在Kotlin中非常类似于Java 8。它们可以包含抽象方法声明，以及实现方法。
他们不同于抽象类的地方：接口不能存储状态。他们可以有属性，但这些需要抽象或提供访问器实现。
 */

interface MyInterface {
    fun bar()
    val prop: Int // abstract  val，子类重写get
    val propertyWithImplementation: String get() = "foo"  //自实现get

    /*
    在接口中，用var 定义属性，语法没问题，但运行有问题。 说明 接口中  还是定义 val这样的 只读属性为好
     */
//    var proper:Int //abstract  var, 子类重写get、set

    fun foo() {
        println("foo")
    }
}

class Child : MyInterface {
    override val prop: Int
        get() = 0x10
//        set(value) {prop = value}  //接口属性不能有setter

    override fun bar() {

    }

    override fun foo() {
        println("Child.foo")
    }
}

interface A {
    fun foo() { println("A") }
    fun bar()
}
interface B {
    fun foo() { println("B") }
    fun bar() { println("bar") }
}
class C : A {
    override fun bar() { println("bar") }
}
class D : A, B {
    override fun foo() {//重写foo()，调用了A、B中的foo()
        super<A>.foo()
        super<B>.foo()
    }

    override fun bar() {//bar()在super中，一个是抽象的，一个是有实现的， 直接super.bar() 会选择有实现的
//        super<B>.bar()
        super.bar()
    }
}

fun main(args: Array<String>) {
    val m = object : MyInterface {
        override fun bar() {
            println(prop)
        }

        override val prop: Int
            get() = -1
    }
    m.foo()
    m.bar()

    var child = Child()
    println(child.prop)

    var d = D()
    d.foo()
    d.bar()
}