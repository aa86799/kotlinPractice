package com.stone.clazzobj.delegation

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/9/2 16:46
 */
//object LazyObject { //饿汉式，线程安全；缺点，类加载时就初始化了，浪费内存
class LazyObject private constructor() {

    companion object {

//        val INSTANCE by lazy { LazyObject() } //懒汉式 单例
//        val INSTANCE by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LazyObject() }  //默认mode，加锁；
        val INSTANCE by lazy(LazyThreadSafetyMode.PUBLICATION) { LazyObject() }  //内部 volatile ，多线程共享
//        val INSTANCE by lazy(LazyThreadSafetyMode.NONE) { LazyObject() }  //单线程，无锁

    }
}

class InstanceObject private constructor(val type: Int) {

    companion object {
        fun getInst(t: Int): InstanceObject {
            return InstanceObject(t)
        }

        val instance = fun(t: Int): InstanceObject {
            return InstanceObject(t)
        }
    }
}

//class InstanceObject private constructor(val type: Int) {
//
//    companion object : Holder<InstanceObject, Int>(::InstanceObject)
//}
//
//open class Holder<out T: Any, in A>(creator: (A) -> T) {
//
//}
