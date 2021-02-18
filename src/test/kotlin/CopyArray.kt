import java.util.*
import kotlin.math.max

/**
 * desc:    二维数组的复制， arraycopy()  不能直接对二维有效， 仅对一维有效；
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/9/20 13:50
 */
fun main() {
    val ary = Array(3) { IntArray(4)}
    for (i in ary.indices) {
        for (j in ary[i].indices) {
            ary[i][j] = i + j * 10
        }
    }
    ary[0][0] = 9
    ary.forEach { println(it.contentToString()) }
//
//    val ary2 = Array(2) { IntArray(3)}
//    for (i in ary.indices) {
//        System.arraycopy(ary[i], 0, ary2[i], 0, ary2[i].size)
//    }
////    val ary2 = Array(2) { IntArray(3)}
////    for (i in ary.indices) {
////        for (j in ary[i].indices) {
////            ary2[i][j] = ary[i][j]
////        }
////    }
////
//    ary2[0][1] = 30
//    ary.forEach { println(it.contentToString()) }
//

    val x1_reain = ary.filterIndexed { index, floats ->
        index >= 1
    }.map { it[0] }
    x1_reain.forEach { println(it) }

    val nary = IntArray(x1_reain.size) {
        max(x1_reain[it], ary[0][0])
    }
    nary.forEach { println(it) }
}

