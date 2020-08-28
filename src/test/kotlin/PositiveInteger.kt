import java.util.*
import kotlin.math.abs

fun main() {
//    println(generatePositiveInteger("adminis"))
//    println(generatePositiveInteger("a"))
//    println(generatePositiveInteger("b"))
//    println(generatePositiveInteger("request_code"))
//    println(generatePositiveInteger("request_code_abc"))
//    println(generatePositiveInteger("request_code_scan_result"))

    val set = hashSetOf<Int>()
    repeat(1000) {//在较大循环次数下，uuid 会重复的; set.size 可能不符合预期
        set.add(generatePositiveInteger(""))
    }
    println("负数：${set.filter { it < 0 }.size}")
    println(set.size)
}

private fun generatePositiveInteger(key: String): Int {
//    return abs(key.hashCode()) shr 16
    return abs(UUID.randomUUID().hashCode()) //shr 16
}