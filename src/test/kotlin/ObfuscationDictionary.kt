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
 * time:    2024/2/7 09:32
 */

private fun obfuscationDictionary() {

    val sourceChs = listOf("i", "I", "l", "o", "0", "O") // 字典字符样本
    val lines = 10_000 // 字典行数
    val maxChLength = 12 // 最大字符数长度
    val dirPath = System.getProperty("user.dir") + "/out/" // 输出目录
    val filename = "obfuscation_dictionary.txt"

    val random = Random.Default

    val chFullList = ArrayList(sourceChs)
    val chIilList = arrayListOf("i", "I", "l")
    val chOo0List = arrayListOf("o", "O", "O")
    val outputList = arrayListOf<String>()
    chFullList.sort()

    val file = File(dirPath)
    if (file.exists()) {
        println("文件已存在，删除")
        file.delete()
    } else {
        file.mkdirs()
        println("开始创建新文件")
    }

    val encoding = "UTF-8"
    var repeatCount = 0

    try {
        val fos = FileOutputStream(File(dirPath, filename))
        val getRandomString = fun(list: List<String>): String {
            return list[random.nextInt(list.size)]
        }
        var i = 0 // 实际添加到输出集合中的次数
        while (outputList.size < lines) {
            var tmp = ""
            val width = nextInt(sourceChs.size)
            for (j in 0 until width) {
                tmp += getRandomString(chFullList)
            }
            if (tmp.length < maxChLength) {
                (0 until (maxChLength - tmp.length)).forEach {
                    tmp += getRandomString(chFullList)
                }
            }
            if (!outputList.contains(tmp)) {
                i++
                outputList.add(tmp)
                fos.write(tmp.toByteArray(charset(encoding)))
                if (outputList.size < lines) {
                    //最后一行不输入回车
                    fos.write('\n'.code)
                }
                repeatCount = 0
            } else {
                repeatCount++
                println("重复生成的字符串当前行数--->$i 内容---> $tmp")
                if (repeatCount == 10000) {
                    println("连续重复次数超过10000次 已达到最大行数 无法继续生成")
                    break
                }
            }
        }
        fos.flush()
        fos.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

fun main() {
    val time = measureTimeMillis {
        obfuscationDictionary()
    }
    println("total: $time ms")
}