import org.junit.Test

class StringCheck {

    @Test
    fun testCheck() {
        // 校验规则：域名判断，六位区划判断，七位编号判断
        val address = "https://abc.efg.com/320402/1001001"
        val domain = "efg.com/"

        if (address.isEmpty()) assert(false)

        if (!address.contains(domain)) assert(false)

        val domainStart = address.indexOf(domain)
        val domainEnd = domainStart + domain.length
        val domainAfter = address.substring(domainEnd)
        assert(domainAfter.matches(Regex("\\d{6}/\\d{7}")))

        val idStart = address.lastIndexOf("/") + 1
        val id = address.substring(idStart)
        assert(id.length == 7)
        println(id)
        println(address.substring("https://abc.efg.com".length + 1, idStart - 1))

        val list = mutableListOf(1, 2, 3)
        list.add(1, 10)
        println(list)
        list.add(4, 20)
        println(list)


//        (1..4).also { range ->
//            range.forEach fe@{
//                if (it % 2 == 0) {
////                    return@also
//                }
//                println("forEach: $it")
//                if (it % 3 == 0) {
//                    return@fe // 相当于 java continue
//                }
//                println("xxx $it")
//            }
//        }

        (1..4).forEach {
            if (it % 2 == 0) {
//                    return@also
            }
            println("forEach: $it")
            if (it % 3 == 0) {
                return@forEach // 相当于 java continue
            }
            println("xxx $it")
        }

        println("---------")
        val rate = 6.6 // $/￥ exchange rate
        val scale = 1/250.0 // day scale
        println(scale)
        println(scale*24*12) // year scale
        val b = 5000_00 // capital
        var sum = 0.0
        println("-----month----")
        (1..24).forEach {// month sum
            sum += b*scale
        }
        println(sum)
        println(sum/b)
        println(sum*rate/100) // real profit
        println("-----year----")
        (1..(24*12)).forEach {// month sum
            sum += b*scale
        }
        println(sum)
        println(sum/b)
        println(sum*rate/100) // real profit
    }
}