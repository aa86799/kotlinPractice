package com.stone.clazzobj.inheritance


/**
 * desc  : 类的继承，方法重写
 * author: stone
 * email : aa86799@163.com
 * time  : 31/05/2017 16 32
 */

open class Base1 //使用open后 表示可被继承
class Derived1 constructor() : Base1() //继承时，用 : 表示继承关系，且需要表明父类的主构造函数

open class Base2(name: String)
class Derived2(fName: String) : Base2(fName)

//子类没有主构造函数，在内部其它构造函数，使用super 调用父类构造函数
open class Base3(name: String)
class Derived3 : Base3 {
    constructor(name: String) : super(name)
}

open class Base4 {
    open fun v() {}
    fun nv() {}
}
class Derived4() : Base4() {
     final override fun v() {}  //final 后  不可被再重写
//    override fun nv() {} //非open的fun 不能被重写
}

open class Foo1 {
    open val x: Int get() {return 1 + 3}
    /*open var x: Int get() {return x} set(value) {x = value}*/

    /*
    当声明了属性的get()后，就不会再自动生成对应的set方法了
    val 声明的属性 是不会有 set方法的， 因其不可变
    属性要被子类重写，也需声明时 加上 open
     */
}
class Bar1 : Foo1() {
    override var x: Int = 9
}

/*
如果有多个超类、接口，且它们定义了相同的成员，子类在重写时：
使用super<superClass/superInterface> 前缀 来标识
 */
open class A {
    open fun f() { print("A") } fun a() { print("a") }
}
interface B {
    fun f() { print("B") } // interface members are 'open' by default
    fun b() { print("b") }
}
class C() : A(), B {
// The compiler requires f() to be overridden:
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f()
    }
}

open class AbsBase {
    open fun f() {}
}
abstract class AbsDerived: AbsBase() {
//    override fun f() {} //直接重写
    override abstract fun f() //抽象重写，还是需要子类重写
}

class StableDerived : AbsDerived() {
    override fun f() {

    }
}

/*
Kt中不能声明static的方法和属性
 */

fun main(args: Array<String>) {
    val d3 = Derived3("stone")

    val f1 = Foo1()
    println(f1.x)
//    f1.x = 1  //
    val b1 = Bar1()
    println(", ${b1.x}")
}

