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
    }
}