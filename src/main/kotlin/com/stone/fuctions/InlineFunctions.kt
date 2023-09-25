package com.stone.fuctions

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KCallable

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 29/07/2017 11 15
 */

fun <T> lock2(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}

fun foo() {

}
//lock2对应的内联函数
inline fun <T> lock3(lock: Lock, body: () -> T): T {
    lock.lock()
    try {
        return body()
    } finally {
        lock.unlock()
    }
}

/*
定义非内联的函数形参: noinline
 */
inline fun foo(inlined: () -> Unit, noinline notInlined: () -> Unit) {

}

/*
在kotlin中使用return，
一般只能用来退出一个非lambda函数(以lambda形式调用高阶函数)。
如果要在lambda函数中，使用return来退出lambda函数，那么对应的高阶函数可以声明成`inline`函数；
如果不想将对应高阶函数定义成`inline`函数，那么在return语句后要加上`@funName`的tag

这是局部return
 */
inline fun ordinaryFunction(op: ()->Unit) {
    op()
}
fun foo2() {
    ordinaryFunction {
        println("执行 op, 退出了 foo2()")
//       return@ordinaryFunction  // ERROR: can not make `foo` return here
       return  // success
    }
    println("foo2")
}

inline fun foreachMy(list: List<Int>, op: (Int) -> Unit) {
    for (item in list) op(item)
}

fun testForeachMy() {
    foreachMy(listOf(1,2,3)) {
        println("foreachMy-$it")

//        println("call return")
//        return // 退出了 testForeachMy()  不再向下执行

        println("call return@foreachMy")
        return@foreachMy // 中止foreachMy的内部的一次函数调用，后续循环中的不影响， 相当于 continue 效果，会继续下一次
    }
    println("foreachMy() 调用后")

    listOf(1,2,3).also {
        foreachMy(it) {
            println("2. foreachMy-$it")
            return@also // 中止foreachMy()
        }
    }
    println("2. foreachMy() 调用后")

}

class TT {
    inline fun ordinaryFunction(op: (TT)->Unit) {
        op(this)
    }
}
//这还是一个  局部的返回
fun foo33() {
    val t = TT()
    t.ordinaryFunction {
        return
    }
    println("foo33")
}

/*
非局部return
 */
fun hasZeros(ints: List<Int>): Boolean {
    ints.forEach {
        //        if (it == 0) return@forEach  // returns from hasZeros
        if (it == 0) return true  // returns from hasZeros
        else println("admin")
    }

    return false
}

/*
在内联函数中，可能没有直接调用lambda函数参数，而是通过一个局部对象中的函数，或其它函数来调用；
这时在调用函数中可能包含非局部返回，这是不允许的，需要使用`crossinline`关键字，指明只能局部返回
 */
inline fun f(crossinline body: () -> Unit) {
    val func = Runnable {
        body()
    }
    //局部对象中
    object {
        fun funX() {
            body()
        }
    }
    // ...
}
fun testF() {
    f {
        listOf(1).forEach(return@f)
//        return@f

        println("ajajaj")
    }
}

/*
以下为具体化关键字：reified  的应用
用于泛型形参上，且函数要以inline修饰，该函数不必须是一个高阶函数
 */
class MyTreeNode(override var name: String, override var parent: TreeNode?) : TreeNode {

}

/*fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && clazz.isInstance(p) && p.parent != null) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}
fun testFindParentOfType(): String? {
    val treeNodeA = MyTreeNode("A", null)
    val treeNodeB = MyTreeNode("B", treeNodeA)
    val treeNodeC = MyTreeNode("C", treeNodeB)

    return treeNodeC.findParentOfType(String::class.java) // 内部反射方法报错
//    return treeNodeC.findParentOfType(TreeNode::class.java)
}*/

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p.parent != null) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

fun testFindParentOfType(): MyTreeNode? {
    val treeNodeA = MyTreeNode("A", null)
    val treeNodeB = MyTreeNode("B", treeNodeA)
    val treeNodeC = MyTreeNode("C", treeNodeB)

//    return treeNodeC.findParentOfType<TreeNode>()  //支持
    return treeNodeC.findParentOfType<MyTreeNode>()  //支持
//    return treeNodeC.findParentOfType()  //支持
//     return treeNodeC.findParentOfType<String?>() //编译时 也是支持的， 运行时报错   当然打开该句，需要返回值为String()
}

inline fun <reified T> membersOf(): Collection<KCallable<*>> {
    return T::class.members
}

class Foox
val fo: Foox
    inline get() = Foox()
var xo: Foox? = null
    set(value) {field = value}
    get() = Foox()
inline var xoo: Foox
    set(value) {value}
    get() = Foox()

fun main(args: Array<String>) {
    val l = ReentrantLock()
    lock2(l) {
        foo()
    }

    lock3(l) {
        foo()
    }

    foo2()

    hasZeros(listOf(3, 4, 0, 8))

    foo33()

    testF()

    val parentNode = testFindParentOfType()
    println(parentNode?.name)

//    val str = membersOf<StringBuilder>().joinToString { kCallable ->  "$kCallable\n" }
//    println(str)

    StringBuilder::class.members
//    String::class.members  //会报错，kotlin并没有对所有java中的Class都支持

    println(xoo)

    testForeachMy()
}


