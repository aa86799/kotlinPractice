package com.stone.basic.types


/**
 * desc  : 基本数据类型  按位操作符
 * author: stone
 * email : aa86799@163.com
 * time  : 30/05/2017 19 14
 */

/*
Type Bit width
Double 64
Float  32
Long   64
Int    32
Short  16
Byte   8
Char 在kotlin中 并不是一个数值类型
不支持8进制， 支持 2、10、16进制
 */

fun basic() {
    var intValue = 7777
    var floatValue1 = 8.3f
    var floatValue2 = 10.45F
    var doubleValue = 9.99
    var longValue = 1L
//    var longValue = 1l //不能用 小写l后缀
    var hexValue = 0XA8a8a8a8a8a8a8a //Hexadecimals  0x或0X开头
    println("hexValue: ${hexValue > Int.MAX_VALUE}")
    var doubleValue2 = 1.3e24 //科学记数法 1.3*10^24
    println("1e24 / (10^20) : ${doubleValue2 / Math.pow(10.0, 20.0)}")
    val binaryValue = 0B00001011 //以 0B或0b 开头
    println("binaryValue : $binaryValue")

    /*
    不像java中 有一个基本类型 float 对应一个 装箱类型  Float
    kotlin中只有 后者
    kotlin中 都能 对应一个 空检查的 装箱类型，即后面加问号：  T?
     */
}

//使用下划线在数值常量赋值数据中，增加可读性
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010

fun equal() {
    val a: Int = 10000
    val b: Int = 10000
    println("1 : ${a === b}") // Prints 'true'
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println("2 : ${boxedA === anotherBoxedA}") // !!!Prints 'false'!!!
    println("3 : ${boxedA == anotherBoxedA}") // Prints 'true'


//    val c: Int? = 1
//    val d: Long? = c // c 不能赋值给 d
//    println("4 : ${c == d}") // Int 和 Long不能 相比

    //像 上面这样的 隐式转换 都行不通的， 只能使用如下明确转换方式: to方法
    val e: Int = 1
    val f: Long = e.toLong()
    /*
    - toByte(): Byte
    — toShort(): Short
    — toInt(): Int
    — toLong(): Long
    — toFloat(): Float
    — toDouble(): Double
    — toChar(): Char
     */

    //类型推断
    val l = 1L + 3 // Long + Int => Long
}

fun bitwise() {
    val r = 1 shl 2 and 0x000FF000
    /*
    bitwise operations 按位操作符：
    — shl(bits) – signed shift left (Java's << )
    — shr(bits) – signed shift right (Java's >> )
    — ushr(bits) – unsigned shift right (Java's >>> )
    — and(bits) – bitwise and (&)
    — or(bits) – bitwise or   (|)
    — xor(bits) – bitwise xor (^)
    — inv() – bitwise inversion (!)
     */
}

fun charOperation() {
    val str = "stone"
    for (c in str) {
        println("char in str : $c")
        val r = c + 3
//        if (r == 118) {//不能如此操作：Char 在kotlin中 并不是一个数值类型
//            println(r)
//        }
        if (r.toInt() == 118) {//可以用toInt() 来进行比较
            println("符合条件的字符$r, 原始字符串的字符是${r - 3}")
        }

        fun decimalDigitValue(c: Char): Int {
            if (c !in '0'..'9')
                throw IllegalArgumentException("Out of range")
            return c.toInt() - '0'.toInt() // Explicit conversions to numbers
        }
//        decimalDigitValue('x')
        decimalDigitValue('6')
    }

    /*
     类似'1'这样单引号中一个字符的定义就是一个 Char
     支持使用\转义: \t , \b , \n , \r , \' , \" , \\ and \$
     Unicode字符: '\uFF00'
     */
}

fun booleanOperation() {
    val b: Boolean = !true
    /* 支持的操作符: || 、 && 、 ! */
}

fun main(args: Array<String>) {

    basic()

    equal()

    charOperation()


}