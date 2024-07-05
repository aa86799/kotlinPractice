import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt
import kotlin.system.measureTimeMillis


/**
 * desc:    混淆字典
 *      使用方式： 将字典文件 aaa.txt 放在 proguard-rules.pro 同级目录下，在 proguard-rules.pro 中加入：
        -obfuscationdictionary obfuscation_dictionary.txt
        -classobfuscationdictionary obfuscation_dictionary.txt
        -packageobfuscationdictionary obfuscation_dictionary.txt
 * author:  stone
 * email:   aa86799@163.com
 * time:    2024/7/5 09:22
 */

private fun generateCombinations(combinations: MutableList<String>, chars: List<String>, maxLength: Int) {

    // 任何两个字符连续出现不超过两次
    fun isValid(s: String): Boolean {
        for (i in 0 until s.length - 2) {
            if (s[i] == s[i + 1] && s[i + 1] == s[i + 2]) {
                return false
            }
        }
        return true
    }

    fun backtrack(current: StringBuilder, length: Int) {
        if (combinations.size == 20_000) return
        if (length > maxLength) return
        if (length > 0 && isValid(current.toString())) combinations.add(current.toString())

        for (char in chars) {
            current.append(char)
            backtrack(current, length + 1)
            current.deleteCharAt(current.lastIndex)
        }
    }

    backtrack(StringBuilder(), 0)

    combinations.forEach { println(it) }
}

fun main() {
//    val sourceChs = listOf("i", "I", "l") // 字典字符样本
    val sourceChs = listOf("o", "0", "O") // 字典字符样本
//    val sourceChs = listOf("i", "I", "l", "o", "0", "O") // 字典字符样本
    val combinations = mutableListOf<String>()
    val maxLength = 12
    val time = measureTimeMillis {
        generateCombinations(combinations, sourceChs, maxLength)
    }
    println("total: $time ms")

    val dirPath = System.getProperty("user.dir") + "/out/" // 输出目录
    val filename = "obfuscation_dictionary2.txt"
    val fos = FileOutputStream(File(dirPath, filename))
    val file = File(dirPath)
    if (file.exists()) {
        println("文件已存在，删除")
        file.delete()
    } else {
        file.mkdirs()
        println("开始创建新文件")
    }

    fos.use {
        combinations.forEach {
            if (it.length < maxLength) return@forEach
            fos.write(it.toByteArray())
            fos.write('\n'.code)
        }
    }
}