import java.lang.ref.PhantomReference
import java.lang.ref.ReferenceQueue

internal object StringMatchAndReplace {
    @JvmStatic
    fun main(args: Array<String>) {
        val queue = ReferenceQueue<Object?>()
        var obj: Object? = Object()
        val ref = PhantomReference(obj, queue);

        obj = null
        println(queue.poll()) // 为null

        System.gc()
        Thread.sleep(1000)  // 等待回收
        println(queue.poll()) // 有值

        val old = "xxBABdsaBaBAbdsxxBABdsaBa   xxBABdsaBaioxxBABdsaBaIjlkxxBABdsaBa   xxBABdsaBa  cbAbw  xxBABdsaBaffbABdsaabxxBABdsaBa"
        val search = "aB"

//        val res = old.replace(search, "<$search>", true)

        println(optItem(old, search))

        val reallyBeautiful = Regex("ab", RegexOption.IGNORE_CASE).replace(old) {
                m -> "<${m.value}>"
        }
        println(reallyBeautiful)

    }

    // 忽略搜索字串大小写，匹配到后替换，且不改变原始子串的大小写
    fun optItem(old: String, search: String): String {
        var start = old.indexOf(search, ignoreCase = true)
        var res = old
        while (start != -1) {
            val subRange = start until  start + search.length
            val replacement = "<${res.substring(subRange)}>"
            res = res.replaceRange(subRange, replacement)

            start = res.indexOf(replacement, start, ignoreCase = true)
            start = res.indexOf(search, start + replacement.length, ignoreCase = true)
        }
        return res
    }
}