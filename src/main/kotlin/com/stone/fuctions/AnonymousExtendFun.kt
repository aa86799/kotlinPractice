package com.stone.fuctions

/**
 * desc:    匿名扩展函数
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2023/2/5 22:04
 */

// 函数中使用，与当前相同类型的 匿名扩展函数
class AnonymousExtendFun1 {

    fun putExtra(): AnonymousExtendFun1 {
        return this
    }

    fun test1(block: AnonymousExtendFun1.() -> Unit) {
        block()
        block(this) // block() 传不传参都可以
        block.invoke(this) // invoke() 必须传参
        println("AnonymousExtendFun1.test1()")
        println("------")
    }

    fun test2(block: AnonymousExtendFun1.() -> Boolean) {
        // 在当前 test2 函数作用域内，可以看作，对 AnonymousExtendFun 扩展了一个 名为 block 的函数
        this.block()
//        this.block(this) // error. 不能传参
//        this.block.invoke() // error. 没有 .invoke() 调用
        println("AnonymousExtendFun1.test2()")
        println("------")
    }

    fun test3(block: (AnonymousExtendFun1) -> Boolean) {
        block(this) // 必须传参
        println("AnonymousExtendFun1.test3()")
        println("------")
    }
}

// 函数中使用，不同于当前类型的 匿名扩展函数    (通常要注意的是这种)
class AnonymousClz {
    fun putExtra(arg: Int): AnonymousClz {
        println("AnonymousClz.putExtra($arg)")
        return this
    }
}
class AnonymousExtendFun2 {

    fun test1(clz: AnonymousClz, block: AnonymousClz.() -> Unit) {
        block(clz) // 必须传参
        block.invoke(clz) // invoke() 必须传参
        println("AnonymousExtendFun2.test1()")
        println("------")
    }

    fun test2(clz: AnonymousClz, block: AnonymousClz.() -> Boolean) {
        // 在当前 test2 函数作用域内，可以看作，对 AnonymousClz 扩展了一个 名为 block 的函数
        clz.block()
//        clz.block.invoke() // error.
        println("AnonymousExtendFun2.test2()")
        println("------")
    }

    // 基于 test1()，匿名扩展函数的实现，可以等价转成 test3()
    fun test3(clz: AnonymousClz, block: (AnonymousClz) -> Boolean) {
        block(clz)
        block.invoke(clz)
        println("AnonymousExtendFun2.test3()")
        println("------")
    }
}

fun main(args: Array<String>) {
    val obj1 = AnonymousExtendFun1()
    obj1.test1 {
        putExtra()
        println("call AnonymousExtendFun1.test1()")
    }

    obj1.test2 {
        println("call AnonymousExtendFun1.test2()")
        true
    }

    obj1.test3 {
        println("call AnonymousExtendFun1.test3()")
        true
    }

    println("********************************************")

    val obj2 = AnonymousExtendFun2()
    val clz = AnonymousClz()
    obj2.test1(clz) {
        this.putExtra(1)
        println("call AnonymousExtendFun2.test1()")
    }

    obj2.test2(clz) {
        this.putExtra(2)
        println("call AnonymousExtendFun2.test2()")
        true
    }

    obj2.test3(clz) {
        it.putExtra(3)
        println("call AnonymousExtendFun2.test3()")
        true
    }

}