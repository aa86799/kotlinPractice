import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

class UnicodeTest {

    @Test
    fun test() {
        val charSymbol = '厂' // '\u5382'
        println(charSymbol.code) // <==> charSymbol.toInt()； => 10进制数
        println("\\u${charSymbol.code.toString(16)}") // 转16进制 => 5382， 就是 unicode 的表示值

        println("-----")

        // kotlin 调用 java api 实现
        val unicode = Integer.toHexString(charSymbol.code)
        println("\\u$unicode")
        println(Integer.parseInt(unicode, 16)) // => 10进制

        val intTen = Integer.parseInt(unicode, 16)
        println(Integer.toHexString(intTen).toCharArray())

        // 测试 repeat()
        GlobalScope.launch {
            repeat(3) {
                println(it)
                delay(1000)
            }
            delay(2000)
            println("end")
        }
        Thread.sleep(10000)
    }
}