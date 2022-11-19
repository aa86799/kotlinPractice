package com.stone.coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main() = runBlocking {

    println("中华人民共和国".dropLast(1))

    /*
     * 怎么模拟 都无法，再次 emit；而 android 中， viewModelScope中，是可以再次 emit的。
     */
//    val flow = getSharedFlow()
//    withContext(Dispatchers.Default) {
//        delay(1000)
//        flow.emit(8888)
//    }


    /*
     * <T1, T2, R> Flow<T1>.combine 操作符，能够合并 不同类型的 flow；
     * Flow<T1> Flow<T2> 返回值为 R
     */
    println(888 / 3 + 18.888 + 100.55)
    val fa = flow { emit(888) }
    val fb = flow { emit(18.888) }
    val fc = flow { emit(100.55) }
    fa.combine(fb) { t1, t2 ->
        val temp = t1 / 3
        temp + t2
    }.combine(fc) { t1, t2 ->
        t1 + t2
    }.collect {
        println("sum: $it")
    }
}

private suspend fun getSharedFlow(): MutableSharedFlow<Int> {
    val flowTest = MutableSharedFlow<Int>()
    flowTest.onStart {
        println("onStart  ${java.lang.Thread.currentThread().name}")
        emit(909999)
        delay(1000)
        emit(8898989)
    }/*.flowOn(Dispatchers.IO)*/.collect {  // collect 会挂起
        println("1-collect: $it  ${Thread.currentThread().name}")
    }
    return flowTest
}



