package com.stone.coroutine

import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

fun main() {

    runBlocking {
        val cur = Calendar.getInstance()
        cur.set(2023, 11, 31)
        cur.add(Calendar.DAY_OF_YEAR, 2)
        println(SimpleDateFormat("yyyy/MM/dd").format(cur.time))

        val list = mutableListOf<Int>(1, 2, 3)
        list.forEachIndexed { index, i ->
            list[index] = i * 10
        }
        println(list)

        println("".split(",").filter { it.isNotEmpty() }.map { it.toInt() })/*
        launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job

interface CoroutineExceptionHandler : CoroutineContext.Element

interface Element : CoroutineContext
         */
        GlobalScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            println("${throwable.message}")
            println("do sth 2-2")
        }) {
            println("do sth 1")
            delay(300)
            suspend {
                println("suspend 1")
                if (Random.nextBoolean()) {
                    throw Exception("exception thrown: suspend")
                }
                println("suspend 2")
            }.invoke()
            if (Random.nextBoolean()) {
                throw Exception("exception thrown")
            }
            println("do sth 2-1")
        }.join()

    }

}