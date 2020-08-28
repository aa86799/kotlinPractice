package com.stone.idioms.conventions.colon


/**
 * desc  : 冒号用于类型定义，接口继承，类继承，类实现接口
 * author: stone
 * email : aa86799@163.com
 * time  : 29/05/2017 14 08
 */
interface Foo<out T : Any> : Bar {//泛型定义中用 out 标识 该泛型只能用于fun的返回类型
    fun foo(a: Int): T
}

interface  Foo2<in T: Any> : Bar {//泛型定义中用  in 标识 该泛型只能用于fun的入参
    fun fooA(a: Int): Int
    fun fooB(t: T): Int
//    fun test():T  //因 使用了in， 所以会报错的
}

interface Operation<T> {//当泛型定义中不使用in、out时  返回值与形参中都可以使用
    fun produce(t: T): T
}

interface Bar  //接口如果无定义fun， 可以省略 {}

abstract class BaseFoo {
    abstract fun print()
}

class MyFoo() : BaseFoo(), Foo<String>, Foo2<Long> {//继承时，父类在冒号后第一个，后面才是其它接口

    override fun print() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fooB(t: Long): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fooA(a: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun foo(a: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}



