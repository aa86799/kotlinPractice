package com.stone.designmode.command

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/7/26 10:14
 */
fun main() {
    //open close edit save

    val performer = object : ICommandPerformer {
        override fun doAction(block: () -> Unit?) {
            block()
            println("doAction")
        }

    }

    performer.doAction {
        println("----open")
    }

    closure(performer)
    edit(performer)
    save(performer)
}

private fun closure(performer: ICommandPerformer) {
    println("----on closing")
    performer.doAction {
        println("close the file")
    }
}

private fun edit(performer: ICommandPerformer) {
    println("----on editing")
    performer.doAction {
        println("edit the file")
    }
}

private fun save(performer: ICommandPerformer) {
    println("----on saving")
    performer.doAction {
        println("save the file")
    }
}