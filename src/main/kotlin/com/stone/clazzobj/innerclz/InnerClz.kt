package com.stone.clazzobj.innerclz

/**
 * desc  : 内部类
 * author: stone
 * email : aa86799@163.com
 * time  : 07/07/2017 11 13
 */

class Outer {//类似java的静态内部类与初始化，内问类不直接持有外部类引用，在内部类中如果要调用外部类fun，需要先new出一个外部类
    private var bar: Int = 1
    class Nested {
        fun foo() {
//            bar = 2 //error
            Outer().bar = 2
        }
    }
}
val demo = Outer.Nested().foo()

class Outer2 {//类似java的普通内部类与初始化，内问类直接持有外部类引用
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}
val demo2 = Outer2().Inner().foo()

class Comb {
    companion object {} // 类似java中的静态内部类， 默认类名 Companion ；外部访问其内部成员时，可以省略Companion类名
//    companion object {} //Companion 类 只能建立一个
    object ABC{} //object 声明类， 是一个单例类，上面的Companion也是
}


class MouseEvent
interface MouseAdapter {
    fun mouseClicked(e: MouseEvent)
    fun mouseEntered(e: MouseEvent)
}
fun addAdapter(adapter: MouseAdapter) {}
//以 object: 来对应java中 匿名内部类的声明
fun load() {
    addAdapter(object : MouseAdapter {
        override fun mouseClicked(e: MouseEvent) {
            // ...
        }
        override fun mouseEntered(e: MouseEvent) {
            // ...
        }
    })

}


fun main(args: Array<String>) {
//    val action = object:  MyInterface {
//        override fun doAction() {
//        }
//
//        override fun doAction2() {
//        }
//
//    }

    val action = MyInterface { }
}