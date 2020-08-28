package com.stone.clazzobj.extension

inline fun extendInit1(noinline init: (ExtensionConstructor.() -> Unit)? = null) = ExtensionConstructor().apply { init?.invoke(this) }

fun extendInit2(init: (ExtensionConstructor.() -> Unit)? = null) {
    init?.invoke(ExtensionConstructor())
}

fun extendInit3(init: (() -> Unit)? = null) = ExtensionConstructor().apply { init?.invoke() }

class ExtensionConstructor {
    constructor() {
        println("ExtensionConstructor()")
    }
}

fun main(args: Array<String>) {
    /*
     * 函数参数为一个 对匿名函数进行扩展的函数.
     * 函数用=，所以直接调用 ExtensionConstructor().
     * 同时apply{} 来调用 传入
     */
    extendInit1 {
        println("init.1")
    }

    extendInit2 {
        println("init.2")
    }

    extendInit3 {
        println("init.3")
    }
}