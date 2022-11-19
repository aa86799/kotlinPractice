package com.stone.clazzobj.delegation

import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

/**
 * desc:    委托属性，内部应用 WeakReference；目标类仍以普通方式使用就可以了
 * author:  stone
 * email:   aa86799@163.com
 * time:    2022/11/19 14:43
 */
class Weak<T : Any>(initializer: () -> T?) {
    var weakReference = WeakReference<T?>(initializer())

    constructor() : this({
        null
    })

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        println("getV")
        return weakReference.get()
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        println("setV")
        weakReference = WeakReference(value)
    }
}

//val aa by Weak<Activity>()
//val bb by Weak { // 自动推断出泛型
//    ""
//}

//val cc: Activity? by Weak<Activity>()
//val dd: Int? by Weak { // 指定了类型，必须是可空的
//    0
//}

private data class WeakBean(var name: String)

fun main() {
    var weak by Weak<WeakBean>()
    println(weak == null) // print getV, true
    weak?.name = "abc" // print getV
    println(weak?.name) // print getV, null

    println("-------")

    weak = WeakBean("stone") // print setV
    println(weak == null) // print getV, false
    weak?.name = "abc" // print getV
    println(weak?.name) // print getV, abc

}
