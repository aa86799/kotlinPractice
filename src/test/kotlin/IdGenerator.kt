import org.junit.Test

class IdGenerator {

    /*
     1. 对前17位数字本体码加权求和
公式为：S = Sum(Ai * Wi), i = 0, ... , 16
其中Ai表示第i位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2

2. 以11对计算结果取模
Y = mod(S, 11)

3. 根据模的值得到对应的校验码
对应关系为：
Y值： 0 1 2 3 4 5 6 7 8 9 10
校验码： 1 0 X 9 8 7 6 5 4 3 2

     */

    @Test
    fun testIdLast() { // 看第18位校验码与计算出来的是否一致
//        val id = "320105198209275127"
//        val id = "371521198411051559"
        val id = "34052419800101001X"
        val weightAry = arrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
        var sum = 0
        id.forEachIndexed { index, c ->
            if (index >= 17) return@forEachIndexed
            // 对前17位数字本体码加权求和
            sum += c.digitToInt() * weightAry[index]
        }

//        以11对计算结果取模
        val mol = sum % 11
        val molIndexAry = arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val verifyAry = arrayOf(1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2)
        println(verifyAry[mol])
        println(verifyAry[molIndexAry[mol]])
    }

    @Test
    fun testFlatMap() {
        val list = ArrayList<ArrayList<T_User>>()
        val sub1 = arrayListOf(T_User("11"), T_User("22"))
        val sub2 = arrayListOf(T_User("33"))
        list.add(sub1)
        list.add(sub2)
        assert(list.flatMap { it }.size == 3)

    }
}

private data class T_User(val name: String)