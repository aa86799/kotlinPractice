package com.stone.designmode.command

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * time:    2020/7/26 10:11
 */
interface ICommandPerformer {

    fun doAction(block: () -> Unit?)
}