package com.stone.idioms.others

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 27/05/2017 18 09
 */
class Complex {

    //Creating a singleton
    object Resource {
        val name = "Name"
    }

    //Default values for function parameters
    fun foo(a: Int = 0, b: String = "") { }

    //Filtering a list
    fun filterList(list:List<Int>) {
//        val positives = list.filter { x -> x > 0 }
        //or
        val positives = list.filter { it > 0 } //it为指代item的 关键字
    }

    //Traversing a map/list of pairs
    fun <K, V> pairsInMap(map: Map<K, V>) {
        for ((k, v) in map) {
            println("$k -> $v -> ${map[k]}")
        }
    }

    //Lazy property
    fun lazy() {
        val p: String by lazy {
            println("in this")
            var a = "aa"
            "aaa" //最后必须给lazy属性赋值  之前可做些其它事
        }
        println("lazy value p = $p")
    }

    //使用? 允许为空类型
    fun getArgOrNull(arg: Int?):Int? {
        return arg
    }


    fun shorthand() {
        //If not null shorthand
        val files = File("KotlinFeature").listFiles()
//        val files = File("KotlinFeature2").listFiles()
        println(files?.size) //files = null， return null
        for (file in files) {
            println(file?.name)
        }

        //If not null and else shorthand
        println(files?.size ?: "stone")  // files = null, return "stone"

//        Executing a statement if null
        val data = mapOf<String, String>(Pair("email", "aa86799@163.com"))
//        val data = emptyMap<String, String>() //empty map
//        val data = mapOf<String, String>() //empty map
        val email = data["email"] ?: throw IllegalStateException("email is missing")

        //Execute if not null
        data?.let {// data != null {  }
            println("data is not null")
        }
    }

    //'try/catch' expression
    fun tryCatch() {
        val result = try {
//            val a = 3 / 0
            val a = 3 / 9
        } catch (e: ArithmeticException) {
            e.printStackTrace()
            throw Exception()
        } finally {

        }
        println("try-catch-> $result") //print: result = kotlin.Unit
    }

    //'if' expression
    fun foo(param: Int) {
        //在把if中的值赋给变量result时，必须要有一个 else
        val result = if (param == 1) {
            "one"
        } else if (param == 2) {
            "two"
        } else {
            "many"
        }
    }

    //Builder-style usage of methods that return Unit
    fun arrayOfMinusOnes(size: Int): IntArray {
        return IntArray(size).apply {fill(-1)} //生成一个size长度的int数组，每个索引都填充成-1
    }

    //Single-expression functions
    fun theAnswer1() = theAnswer()
//    fun theAnswer1() = 42
    fun theAnswer(): Int {
        return 42
    }

    //fun:Int 直接 = when表达式
    fun transform(color: String): Int = when (color) { "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }

    fun withObjetc(obj: Complex) {
        //使用with(object) ，在block中 可直接调用 对象实例的 fun, 不需要obj.前缀
        with(obj) {
            foo()
            foo(2)
        }
    }

    //Java 7's try with resources
    fun stream() {
        val stream = Files.newInputStream(Paths.get("README.md"))
        stream.buffered().reader().use { reader -> println(reader.readText()) }
    }

    //    private fun <T: Any> fromJson(str: String, clazz: Class<T>) {
    /*private*/ fun <T> fromJson(str: String, clazz: Class<T>): T? {
        return null
    }
    /*
    inline 内联一个方法， 前面为fun定义，且使用了泛型，后面直接 = 内联的方法
     */
    inline fun <reified T: Any> fromJson(str: String): T? = this.fromJson(str, T::class.java)

    fun booleanCheck(bool: Boolean?) {
        val bool: Boolean? = true
        if (bool == true) {//Boolean? 的比较 不能省略 ==...

        } else {
            //bool is false or null
        }
    }

    fun unit() {
        // ": Unit" is omitted here
//        return Unit  //默认没有返回值时，实际上返回的是 Unit，它与java中的 Void 类似  注：java中不能直接写上返回void的
    }

    /*
    在一些case中，无参数的函数 可能与 只读属性 间 是可以互换的。

     */
    fun calc() = -3 * 1000
    val result = calc()
    fun calcResult() = if (result > 0) {
        result
    } else {
        throw ArithmeticException()
    }
}

fun main(args: Array<String>) {

    println(Complex.Resource.name)

    val complex = Complex()

//    val map = hashMapOf<String, Int>(pairs = Pair("a", 1))
    val map = hashMapOf(Pair("a", 1), Pair("b", 2))
    complex.pairsInMap(map)

    complex.lazy()

    println(complex.getArgOrNull(null))

    complex.shorthand()

    complex.tryCatch()

    val array = complex.arrayOfMinusOnes(5)
    for (index in array.indices) {
        print("${array[index]} ")
        if (index == array.indices.maxOrNull()) {
            println()
        }
    }
    complex.stream()

    complex.calcResult()

}