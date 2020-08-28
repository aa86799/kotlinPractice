package com.stone.designmode.watcher

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 10:33
 */
class SObservable<T> {

    private val list: MutableList<SObserver<T>> = mutableListOf()
    var num = 0
    private val NOT_SET = Any()
    private var mData: Any?

    constructor() {
        mData = NOT_SET
    }

    constructor(data: T) {
        mData = data
    }

    fun getValue(): T? {
        return if (mData != NOT_SET) {
            mData as? T
        } else {
            null
        }
    }

    fun setValue(data: T) {
        mData = data
    }

    fun registerObserver(observer: SObserver<T>) {
        list.add(observer)
        println("registerObserver $observer")
    }

    fun unregisterAll() {
        list.clear()
        println("unregisterAll")
    }

    fun notifyDataSet() {
        part1()
        list.forEach {
            it.observe(this)
        }
        part2()
    }

    private fun part1() {
        println("notify part1")
    }

    private fun part2() {
        println("notify part2")
    }
}