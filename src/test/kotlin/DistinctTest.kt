import org.junit.Test

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/9/2 20:43
 */
class DistinctTest {

    data class Data(val a: Int, val b: Long)

    @Test
    fun test() {
        val list = listOf(8888888_1200, 8888888_1000,8888888_1024,
            8888888_1900,8888888_1400,8888888_1400, 8888888_1200)
//        list.map { it / 1000 }.distinct().forEach {
        list.distinctBy { it / 1000 } .forEach {
            println(it)
        }


        val list2 = listOf(Data(1,8888888_1200), Data(2,8888888_1000), Data(3,8888888_1024),
            Data(4,8888888_1900), Data(5,8888888_1400), Data(6,8888888_1400), Data(7,8888888_1200))
        list2.distinctBy {it.b / 1000}.map { Data(it.a, it.b / 1000) } .forEach {
            println(it)
        }
        assert(list.isNotEmpty())
    }
}