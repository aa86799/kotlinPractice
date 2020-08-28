package com.stone.designmode.watcher

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 10:42
 */
fun main() {

    val observable = SObservable<User>()
    observable.registerObserver(object : SObserver<User> {
        override fun observe(observable: SObservable<User>) {
            println("obsere 1: ${observable.num * 10}")
            val age = observable.getValue()?.age ?: 1
            println("obsere 1: ${age * 10}")

        }
    })

    observable.registerObserver(object : SObserver<User> {
        override fun observe(observable: SObservable<User>) {
            println("obsere 2: ${observable.num shl 10}")
            val age = observable.getValue()?.age ?: 1
            println("obsere 2: ${age shl 10}")
            println(1024*18)   //   18 << 10    <===>  (1 << 10) * 18
        }
    })

    observable.num = 2
    observable.notifyDataSet()

    println("--------")
    observable.setValue(User("Apolo", 18))

    observable.notifyDataSet()

    observable.unregisterAll()
}