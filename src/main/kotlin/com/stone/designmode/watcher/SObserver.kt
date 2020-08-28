package com.stone.designmode.watcher

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 10:33
 */
interface SObserver<T> {

    fun observe(observable: SObservable<T>)
}