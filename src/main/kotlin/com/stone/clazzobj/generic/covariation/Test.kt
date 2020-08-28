package com.stone.clazzobj.generic.covariation

class S<E> {
    val list = mutableListOf<E>()
    fun add(e: E) {
        list.add(e)
    }

    fun get(index: Int): E {
        return list[index]
    }

    fun set1(s: S<in E>, e: E) {
        s.list.add(e)
        val el = s.list[0]

    }

    fun set2(s: S<out E>, e: E) {
//        s.list.add(e)
        val el = s.list[0]

    }
}

class A<out E> {
//    fun get(p: E): E { //不能作参数
//        return p
//    }

//    fun get(s: S<E>) { //不能作参数
//
//    }

//    val s:S<E> = S<E>() //不行

    /* constructor(s: S<E>) { //行的
 //        s.list.add() //E 类型，无法做为参数， 这里add(e) 也无法进行
         val e: E = s.list[3]
         e.toString()
     }*/

}

class B<in E> {
//    fun get(p: E): E { //不能作返回值
//        return p
//    }

    //    fun get(s: S<E>) {// 提示说是 不变的
//    fun get(s: S<in E>) {// 提示说是 要用 out
    fun get(s: S<out E>) {//

    }

//    constructor(e: E) {} //行的
}

class C<in E : SuperClass> {

    inline fun <reified E> m(pp: ArrayList<E>, p: Any) {
//        pp.add(p)
    }
}

fun main(args: Array<String>) {
    val a: A<SuperClass> = A<ChildClass>() //仅适合 out K
    val b: B<ChildClass> = B<SuperClass>()  //仅适合 in K

//    val c1: C<out SuperClass> = C<SuperClass>()
    val c2: C<in SuperClass> = C<SuperClass>()   // <==> val c2: C<SuperClass> = C<SuperClass>()
//    val c3: C<in SuperClass> = C<ChildClass>()
    val c4: C<*> = C<SuperClass>()

    println(256 or 1024 or 512)
    println(256 + 512 + 1024)
}