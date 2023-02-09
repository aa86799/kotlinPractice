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
    }
}