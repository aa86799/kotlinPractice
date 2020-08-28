package com.stone.array

import kotlin.random.Random

/**
 * desc:    二维数组 左右旋转，行内左右逆序。 上下逆序未实现。
 *          测试结果发现，对于 外行内列、外列内行实现 的 左右旋转、左右或上下逆序，它们的结果之间有对应关系
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/2/29 10:25
 */
class ArrayTest {
    private val random = Random(100)
    inner class Blob(
        var x: Int, //x方向坐标索引
        var y: Int, //y方向坐标索引
        var value: Int,
        var pX: Int = x * 20, //坐标点的x值。如单位间隔20
        var pY: Int = y * 20 //坐标点的y值。如单位间隔20
    )

    fun createArray(row: Int, col: Int) = Array(row) { rowE -> Array(col) { colE -> Blob(rowE, colE, random.nextInt(100)) } }

    /*
     * 对于二维数组，每个元素是一个一维数组。
     * 双层for循环，外层行、内层列方式遍历； 每输出一行后，换行。
     * 每一行上的元素数量 = col 的值，
     * 每一列上的元素数量 = row 的值。
     *
     * 每输出的一行元素，对应实际问题中的，如图片、棋盘等，表示的是 它们的 一列元素。
     */
    fun dumpArray(array: Array<Array<Blob>>) {
        println("外行，内列，输出一行表示实际一列")
        var mapX: Int
        var mapY: Int
        for (rowIndex in array.indices) {
            for (colIndex in array[rowIndex].indices) {
                mapX = array[rowIndex][colIndex].x
                mapY = array[rowIndex][colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
//                print("${array[mapX][mapY].value}(${mapX},${mapY})[${array[mapX][mapY].pX}, ${array[mapX][mapY].pY}] ")
            }
            println()
        }
        println()
    }

    /*
     * 对于二维数组，每个元素是一个一维数组。
     * 双层for循环，外层列、内层行方式遍历； 每输出一行后，换行。
     * 每一行上的元素数量 = row 的值，  行上的元素 = dumpArray(array) 列上的元素。
     * 每一列上的元素数量 = col 的值，  列上的元素 = dumpArray(array) 行上的元素。
     *
     * 每输出的一行元素，对应实际问题中的，如图片、棋盘等，表示的是 它们的 一行元素。
     *
     * 两种方式，均可以将 二维数组的每个元素遍历到。
     * 本函数的输出，看起来像是 dumpArray 函数输出 右(顺时针)旋转了90度，并且左右逆向了
     */
    fun dumpArray2(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("外列，内行，输出一行表示实际一行")
        var mapX: Int
        var mapY: Int
        for (colIndex in 0 until col) {
            for (rowIndex in 0 until row) {
                mapX = array[rowIndex][colIndex].x
                mapY = array[rowIndex][colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    /*
     * dumpArray输出，直接 右(顺时针)旋转90度。
     * 新的第一行数据是 0(3,0) 0(2,0) 0(1,0) 0(0,0)  ， 与原第一列的数据 0(0,0) 0(1,0) 0(2,0) 0(3,0)  左右逆向了
     *
     * 结果，x横向递减，y纵向递增
     */
    fun dumpArrayRotateClockwise(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外行，内列，顺时针旋转90度，与 '对外列，内行，左右逆序' 结果一样。编号 A")
        var mapX: Int
        var mapY: Int
        for (colIndex in 0 until col) {
            for (rowIndex in 0 until row) {
                mapX = array[row - 1 - rowIndex][colIndex].x
                mapY = array[rowIndex][colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    /*
     * dumpArray输出，直接 左(逆时针)旋转90度。
     * 新的第一行数据是 0(0,4) 0(1,4) 0(2,4) 0(3,4)  ， 与原最后一列的数据 相同
     *
     * 结果，x横向递增，y纵向递减
     */
    fun dumpArrayRotateAnticlockwise(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外行，内列，逆时针旋转90度，与 '外列，内行 上下逆序' 结果一样。编号 D")
        var mapX: Int
        var mapY: Int
        for (colIndex in 0 until col) {
            for (rowIndex in 0 until row) {
                mapX = array[rowIndex][rowIndex].y
                mapY = array[rowIndex][col - 1 - colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    fun dumpArray2RotateClockwise(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外列，内行，顺时针旋转90度, 与 '对外行，内列，左右逆序' 结果一样。编号 B")
        var mapX: Int
        var mapY: Int
        for (rowIndex in 0 until row) {
            for (colIndex in 0 until col) {
                mapX = array[rowIndex][rowIndex].y
                mapY = array[rowIndex][col - 1 - colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    /*
     * 第一行变成 0(3,0) 0(3,1) 0(3,2) 0(3,3) 0(3,4)
     */
    fun dumpArray2RotateAnticlockwise(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外列，内行，逆时针旋转90度，与 '对外行，内列 上下逆序' 结果一样。编号 C")
        var mapX: Int
        var mapY: Int
        for (rowIndex in 0 until row) {
            for (colIndex in 0 until col) {
                mapX = array[row - 1 - rowIndex][rowIndex].x
                mapY = array[row - 1 - rowIndex][colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    fun dumpArrayReverseRc(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外行，内列，左右逆序，与 '对外列，内行，顺时针旋转90度' 结果一样。编号 B")
        var mapX: Int
        var mapY: Int
        for (rowIndex in 0 until row) {
            for (colIndex in 0 until col) {
                mapX = array[rowIndex][rowIndex].y
                mapY = array[rowIndex][col - 1 - colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }

    fun dumpArray2ReverseCr(array: Array<Array<Blob>>, row: Int, col: Int) {
        println("对外列，内行，左右逆序，与 `对外行，内列，顺时针旋转90度` 结果一样。编号 A")
        var mapX: Int
        var mapY: Int
        for (colIndex in 0 until col) {
            for (rowIndex in 0 until row) {
                mapX = array[row - 1 - rowIndex][colIndex].x
                mapY = array[rowIndex][colIndex].y
                print("${array[mapX][mapY].value}($mapX,$mapY) ")
            }
            println()
        }
        println()
    }
}

fun main() {
    val instance = ArrayTest()
    val row = 4
    val col = 5
    val ary = instance.createArray(row, col)
    println("size=${ary.size}")
    instance.dumpArray(ary)
    instance.dumpArray2(ary, row, col)
    instance.dumpArrayRotateClockwise(ary, row, col)
    instance.dumpArrayRotateAnticlockwise(ary, row, col)
    instance.dumpArray2RotateClockwise(ary, row, col)
    instance.dumpArray2RotateAnticlockwise(ary, row, col)
    instance.dumpArrayReverseRc(ary, row, col)
    instance.dumpArray2ReverseCr(ary, row, col)
}

