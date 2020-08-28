package com.stone.clazzobj.objectclz

/**
 * desc  : object修饰符
 * author: stone
 * email : aa86799@163.com
 * time  : 07/07/2017 13 47
 */


open class A(x: Int) {
    open val y: Int = x
}
interface B {
    // ...
}

val cc = object : A(5) {

}

fun test1(): A {
    return object : A(8){}
}

class BO {
    private fun test2()= object {
        val x = 77
    }
    fun test3() = object {
        val x = 88
    }

    fun bar() {
        val x2 = test2().x // worked

        /*
        public的 函数， 其返回值为匿名对象，内部的公共成员 无法被访问
         */
//        val x3 = test3().x // ERROR: Unresolved reference 'x'
    }
}

//kotlin匿名对象的 可访问性， 类似java中匿名内部类   (kotlin 匿名对象中 访问外面的属性时，不需要final 关键字)
//fun countClicks(window: JComponent) {
//    var clickCount = 0
//    var enterCount = 0
//    window.addMouseListener(object : MouseAdapter() {
//        override fun mouseClicked(e: MouseEvent) {
//            clickCount++
//        }
//        override fun mouseEntered(e: MouseEvent) { enterCount++
//        }
//    })
//  // ...
//}


//以object 声明一个 class， 表示这是一个单例类
//object DataProviderManager {
//    fun registerDataProvider(provider: DataProvider) {
//        // ...
//    }
//    val allDataProviders: Collection<DataProvider> get() = // ...
//}

//call
//DataProviderManager.registerDataProvider(...)


//Such objects can have supertypes:
//object DefaultListener : MouseAdapter() {
//    override fun mouseClicked(e: MouseEvent) {
//        // ...
//        }
//    override fun mouseEntered(e: MouseEvent) {
//        // ...
//    }
//}

//object class 不能嵌套在一个函数内
//fun cc() {
//    object AA {}
//}

//可嵌套在一个object class中
//object Outer {
//    object Internal
//}

//可嵌套在一个非inner class中  (inner class 即java中的 成员内部类)
class Outer {
//    inner class Internal {
//        object Inter
//    }

    class Internal {
        object Inter
    }
}

//用在companion关键字后， 可见 companion类 是一个非 inner class
class MyClass {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

//companion object可以有自己的静态方法和属性     关于@JvmStatic  java中如何调用kotlin 使用类似 静态调用语义
//interface Factory<T> { fun create(): T
//}
//class MyClass {
//    companion object : Factory<MyClass> {
//        override fun create(): MyClass = MyClass()
//    }
//}

/*
object func expressions 是被立即执行且初始化的 (初始化一个匿名对象)
object 声明一个类，是 initialized lazily， 在首次被访问时，才初始化
companion object，这是一个静态内部类，即在类加载时就会被初始化，与java中 静态块 "static {}" 语义相匹配
 */

fun main(args: Array<String>) {
    //使用object，还能声明一个 子对象， 可以继承超类，和实现接口
    val ab: A = object : A(1), B {
        override val y = 15
    }

    fun foo() {
        val adHoc = object {
            var x: Int = 0
            var y: Int = 0
        }
        print(adHoc.x + adHoc.y)
    }

}