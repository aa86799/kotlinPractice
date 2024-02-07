import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.nio.charset.Charset

class Test1 {

    @Test
    fun testFangdai() {
        val dkje = 100_000
        val yll = 4.65 / 100 / 12
        val ys = 240.0
            var yhjeCalc = (dkje * yll * Math.pow(1+yll, ys)) / (Math.pow(1+yll, ys) - 1)
        println("月还：$yhjeCalc")
    }

    @Test
    fun testMySuspendingFunction() = runBlocking<Unit> {
//        val aa: Int? = null
//        aa ?: return@runBlocking

        val gitVersionProcess = ProcessBuilder("git", "-version").start()
        gitVersionProcess.waitFor()
        val gitVersion = gitVersionProcess.inputStream.readBytes().toString(Charset.forName("utf-8"))
        println(gitVersion)

        println("reg: ${Regex("\\*d\\*").matches("*d*")}") // true
        println("reg: ${Regex("0\\d{8}").matches("012341234")}") // true  0后，8位数字
        println("reg: ${Regex("0\\d{6,8}").matches("01234123")}") // true  0后，6到8位数字

        /*(0..100).forEach {
            val snum = "$it.99"
//            val dnumber = snum.toDouble() * 100.00 // double 计算精度有问题
            val dnumber = BigDecimal(snum).multiply(BigDecimal(100))
            println(dnumber)
        }*/

        println("abc/cef/gbh.jpg".substringAfter("/"))
        println("abc/cef/gbh.jpg".substringAfterLast("/"))
        println("abc/cef/gbh.jpg".substringBefore("/"))
        println("abc/cef/gbh.jpg".substringBeforeLast("/"))
        println("abc/cef/gbh.jpg".substringAfterLast("/").substringBefore("."))

        val a = false
        val b = false
        val c = true
        val d = true
        assert(a && b || c && d) // true  &&先执行

        assert(ClzJava.AA == 10)

        assert(String.format("aaa.%s.aaa", "bb") == "aaa.bb.aaa") // true

        assert("002".toInt() == 2) // true

//        assert((1..20).contains(20))
//        assert((1 to 20).toList().size == 2)
//
//        println(Calendar.getInstance().timeZone.rawOffset / 3600_000)
////        val rawOffset = TimeZone.getDefault().rawOffset
//        val rawOffset = TimeZone.getTimeZone(ZoneId.of("America/New_York")).rawOffset
//        val symbol = if (rawOffset > 0) "+" else "-"
//        val offsetHour = abs(rawOffset) / 3600_000
//        val offsetMinute = abs(rawOffset) % 3600_000 / 60_000
//        val s =  "$symbol${String.format("%1$02d", offsetHour)}:${String.format("%1$02d", offsetMinute)}"
//        println(s)
//
//        var a = 1
//        var b = 2
//        b = a.also { a = b }
//        assert(a == 2)
//        assert(b == 1)
//
//        b = a.apply { a = b }
//        assert(a == 1)
//        assert(b == 2)
//
//        fun getVersion(): String {
//            var version = "admin_3892"
//            val index = version.indexOf("_")
//            if (index != -1) {
//                version = version.substring(0, index)
//            }
//            return version
//        }
//        assert(getVersion().length  == 5)

//        val name = "file:///sdcard/abc/ddd/efg.jpg"
//        println(File(name).name)
//        assert(File(name).name == name) // efg.jpg != name

        val list = mutableListOf<Int>()
        println(list.joinToString(","))
        val list1 = listOf(1,2,3)
        println(list1.joinToString(","))

    }
}