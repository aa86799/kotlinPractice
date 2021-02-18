package leetcode.array

/**
 * desc:    https://leetcode-cn.com/problems/move-zeroes/   no.283
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/9/5 10:00
 */
fun main() {
    moveZeroes(intArrayOf(0, 1, 4, 3, 12))
//    leetcode.array.moveZeroes(intArrayOf(2,1))
//    leetcode.array.moveZeroes(intArrayOf(1))
}

fun moveZeroes(nums: IntArray): Unit {
//遇到0元素，前一个和当前元素交换位置
    var i = 0
    var j = 0 //目标索引指针
    /*while (i < nums.size) {
        if (nums [i] != 0) {
            //if (i>j)   // 加上它，解决在首位非0时，自己与自己交换
            leetcode.array.swap(nums, i, j)
            j++
        }
        i++
    }*/

    while (i < nums.size) {
        if (nums[i] != 0) {
            if (i != j) {
                nums[j] = nums[i] //非零 i 赋值给 j
                nums[i] = 0 //i 位置上就是0了
            }
            j++
        }
        i++
    }

    nums.forEach {
        println(it)
    }
}

private fun swap(nums: IntArray, i: Int, j: Int) {
    val temp = nums[i]
    nums[i] = nums[j]
    nums[j] = temp
}