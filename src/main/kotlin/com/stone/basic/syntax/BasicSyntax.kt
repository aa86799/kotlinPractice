package com.stone.basic.syntax

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 27/05/2017 11 01
 */
class BasicSyntax {

    //Function having two Int parameters with Int return type:
    public fun sum(a: Int, b: Int): Int {//访问修饰符 省略时，默认为 public
        return a + b
    }

    //Function having three Int parameters with Int return type:
    fun sum(a: Int, b: Int, c: Int) = a + b + c

    //Function returning no meaningful value:
    fun printSum(a: Int, b: Int): Unit {//Unit为无类型，类似java中的void，可以省略
        println("sum of " + a + " and " + b + " is ${a + b}")
        println("sum of $a and $b is ${a + b}") //在双引号中 直接用 $符操作变量   与上句等价
    }

    fun assignVarible() {
        val a: Int = 1 // immediate assignment    val = 本地只读变量 即不可变 immutable
        val b = 2 // `Int` type is inferred  自动类型推断
        val c: Int // Type required when no initializer is provided
        c = 3 // deferred assignment

        var x = 1 // Mutable variable:
        x++

        val s1 = "x is $x"  // simple name in template:
        val s2 = "${s1.replace("is", "was")}, but now is $x" // arbitrary expression in template:
        println(s2)
    }

    fun maxOf(a: Int, b: Int): Int {
//        return a > b ? a : b; //原java中的三目运算符 不可用

        if (a > b) return a
        else return b
    }

    //fun maxOf(a:Int, b: Int):Int
    fun minOf(a: Int, b: Int)/*: Int*/ = if (a < b) a else b

    //字符串转int
    private fun parseInt(str: String): Int? {// ? 表示可以为空
        return str.toIntOrNull(8)//参数为 进制数(radix), 不传默认为10   转换错误 返回null
    }

    fun getBaseSyntax(name: String?): BasicSyntax? { // ? 表示可以为空
//        checkNotNull(name) // 参数不能为空的 检测函数: 为null抛出异常
        return BasicSyntax()
    }

    fun printProduct(arg1: String, arg2: String) {
        val x1 = parseInt(arg1)
        val x2 = parseInt(arg2)
        if (x1 == null || x2 == null) return
        println(x1 * x2)
    }

    //is operator
    fun getStringLength1(obj: Any): Int? { //Any 是任何Kotlin类的超类
        if (obj is String) {// 类似java中的 instanceof
// `obj` is automatically cast to `String` in this branch
            return obj.length
        }
// `obj` is still of type `Any` outside of the type-checked branch
        return null
    }

    // !is
    fun getStringLength2(obj: Any): Int? {
        if (obj !is String) return null
        return obj.length
    }

    fun getStringLength3(obj: Any): Int? {
        if (obj is String && obj.length > 0)
            return obj.length
        return null
    }

    //Using a for loop
    fun foreachItems() {
//        val items = listOf<String>("apple", "banana", "kiwi")
        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {//in operator
            println("item is $item")
        }
        for (index in items.indices) {//indices 索引  type: Collection
//            println("item at $index is ${items.get(index)}")
            println("item at $index is ${items[index]}") //使用[index]  而不用 .get(index)
        }
    }

    //Using when expression
    fun describe(obj: Any): String =
            when (obj) {//when 中 必须 有一个else
                1 -> "One"
                "Hello" -> "Greeting"
                is Long -> "Long"
                !is String -> "not a string"
                else -> "Unknown"
            }

    //Using ranges  如果在if中 check的是一个数值，且使用了 in operator
    fun range() {
        val x = 10;
        val y = 9  //同一行中使用 ; 来分隔
        if (x in 1..y + 1) {//使用 .. 来表示范围   最后转换成 x in 1..10
//        if (x in (1..(y + 1))) {//如此解释 执行顺序 没问题  最后转换成 x in 1..10
//        if (x in ((1..y) + 1)) {如此解释 执行顺序 不行   最后转换成  x in 10
            println("fits in range")
        }

        for (x in 1..5) {//include 5

        }

        for (x in 1..10 step 2) {//x+=2   x is in {1, 3, 5, 7, 9}
            println("rang 1..10 step 2: $x")
        }

        for (x in 9 downTo 0 step 3) {//x=9, x>=0 x-=3
            println("x in 9 downTo 0 step 3: $x")
        }

        for (x in 0 until 10 step 2) {//until 10 ： not include 10
            println("x in 1 until 10: $x")
        }
    }

    //Checking if a collection contains an object using in operator:
    fun contains() {
        val list = listOf("a1", "a2", "a3") //不可变list
        when {// 匹配到一个条件 其它 就不再匹配
            "a4" in list -> println("壹")
            "a5" in list -> println(list.size)
            "a3" in list -> println("the index is ${list.indexOf("a3")}")
        }
    }

    //Using lambda expressions to filter and map collections:
    fun collectionsLambda() {
//        val list = mutableListOf<Int>()  //可变list
//        for (i in 1 ..10) {
//            list.add(i)
//
//        }

        val list = (1..10).toList() //上面的 简写
        IntRange(1, 10).toList()  // (1..10) 就是IntRange   还有其它的 LongRange CharRange
        list.filter { it % 2 == 0 }.map { it * 3 }.forEach(::println)
//      list.filter { it % 2 == 0 }.map { it * 3 }.forEach{ println("item is $it")}
    }

}

fun main(args: Array<String>) {
    var base = BasicSyntax()
    val suma = base.sum(1, 2, 3)
    println(suma)
    base.printSum(10, 20)

    base.assignVarible()

    var min = base.minOf(10, 20)
    println("min number is $min")

    base.getBaseSyntax(null)

    base.printProduct("1", "kk")
    base.printProduct("33", "66")

    println(null) //直接输出了 null 字符串

    base.foreachItems()

    println(base.describe(2))

    base.range()

    base.contains()

    base.collectionsLambda()


}
