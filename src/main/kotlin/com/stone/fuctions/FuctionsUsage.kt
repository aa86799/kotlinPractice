package com.stone.fuctions

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 28/07/2017 02 33
 */

/*
使用infix关键字, 特征:
> 可用在扩展函数、成员函数前
> 参数只有一个
在后续调用时， 可以形如：x <funName> y
 */
infix fun Int.shll(x: Int): Int {
    return this shl x
}

/*
参数必须显示声明类型
含默认值的参数列表，调用时可以省略 传入有默认值的参数；以减少函数重载数量
(当然，如果三个以上参数，中间有参数有默认值，后面参数没有，调用时是不能省略传参的)
 */
fun read(a: Int, b: Int = 0, c: Int = 0) {
    /* 调用如下 */
    read(1)
    read(1, 2)
    read(1, 2, 3)
}

/*
基类 open一个含默认值参数的函数，子类要override：子类重写函数不能有默认值参数
 */
open class A {
    open fun foo(i: Int = 10) {  }
}
class B : A() {
    override fun foo(i: Int) {  } // no default value allowed
}

/*
命名参数，即调用时，带上参数名，格式：参数名=参数值
注意：如果前一个参数是命名参数，后一个就不能是非命名的；命名参数的调用名称，与对应参数的声明时名称需要一致
优点：增加可读性；有多个带默认值的参数时，还可以在调用时，跳过中间的参数

 */
fun reformat(str: String,
             normalizeCase: Boolean = true,
             upperCaseFirstLetter: Boolean = true,
             divideByCamelHumps: Boolean = false,
             wordSeparator: Char = ' ') {
}

/*
返回值为Unit：相当于返回一个空对象；可以在函数声明中，省略返回值定义；也可以在函数实现中，省略返回值语句
 */
fun printHello(name: String?): Unit {// <==> fun printHello(name: String?)
    if (name != null) {
        return Unit
    } else {
        //省略返回语句
    }
}

/*
 * 单个函数表达式：当函数仅返回一个表达式，可以省略函数主体(函数块)声明，形如 fun a(p: Type) = b
 */
fun doubleMultiply(x: Int): Int = x * 2 // <==> fun double(x: Int) = x * 2

/*
Variable number of arguments (Varargs)
可变参数：java中使用`...`，kotlin中使用 `vararg`关键字
与java类似，kotlin中的可变参数对象，也是一个数组，即 Array类型
 */
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    result.addAll(ts) // ts is an Array
    return result
}
/*
可变参数只能有一个，如果声明在其它参数前：
系统无法自动推断，可以使用命名参数语法进行调用；
将一个可变参数，传递给另一个函数中的可变参数，要在可变参数前使用`*`符号
 */
fun <T> asList2(vararg ts: T, a: Int): List<T> {
    return asList(*ts)
}

/*
如果在可变参数前，有一个函数型参数：
调用时，书写不好看，不推荐
 */
fun <T> asList3(op: () -> Unit, vararg ts: T): List<T> {
    return asList(*ts)
}

/*
函数型参数在后面(不论前面是不是可变参数)：
可以将函数，直接写到参数外
 */
fun <T> asList4(vararg ts: T, op: () -> Unit): List<T> {
    return asList(*ts)
}

/*
函数范围：
函数可以声明在一个顶级的文件中，即kotlin-file中；
可以声明成一个类的成员函数、类的扩展函数；
可声明成一个局部函数，即函数中声明的函数；局部函数可以访问外部函数中的局部变量
 */

/*
满足尾递归形式的函数(不一定要是函数表达式)，使用 `tailrec`关键字，
编译器中会优化出一个更有效率的循环语句替代，防止栈溢出。
注意：在 try/catch/finally 语句块中，进行尾递归调用，`tailrec` 无法优化
 */
tailrec fun findFixPoint(x: Double = 1.0): Double
        = if (x == Math.cos(x)) x else findFixPoint(Math.cos(x))
//上面的递归，相当于如下循环
private fun findFixPoint(): Double {
    var x = 1.0
    while (true) {
        val y = Math.cos(x)
        if (x == y) return y
        x = y
    }
}



fun main(args: Array<String>) {
    val r = 1 shll 3  // <==> 1.shll(3)
    println(r)

    reformat("") //省略默认参数
    reformat(str = "",
            normalizeCase = true,
            upperCaseFirstLetter = true,
            divideByCamelHumps = false,
            wordSeparator = '_')
    reformat(str = "", wordSeparator = '_')


    //调用有可变参数的函数
    asList(1, 2, 3)
    asList2(ts = *arrayOf(1, 2, 3), a = 4)
    asList3({

    }, 1, 2)
    asList4(1, 2, op = {

    })
    asList4(1, 2) {

    }

    println()
    mapOf(Pair("a", 1), Pair("b", 2)).forEach { t, u -> println("t=$t, u=$u") }
    mapOf(Pair("a", 1), Pair("b", 2)).forEach { _, value -> println("$value!") }//函数型参数，它的其中有不使用的参数，可以用`_`声明
    mapOf(Pair("a", 1), Pair("b", 2)).forEach { _, _ -> println("3!") }//函数型参数，它的其中有不使用的参数，可以用`_`声明


}
