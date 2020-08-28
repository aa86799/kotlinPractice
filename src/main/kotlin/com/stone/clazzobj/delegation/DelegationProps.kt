package com.stone.clazzobj.delegation

import java.util.*
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KCallable
import kotlin.reflect.KProperty

/**
 * desc  : 委托属性
 * author: stone
 * email : aa86799@163.com
 * time  : 13/07/2017 10 46
 */

class Delegate {
    /*
    thisRef : 其类型，必须是委托属性的所有者或其super类型
    property : 必须是KProperty<*>或其super类型 KCallable<*>

     */

    operator fun getValue(thisRef: Super?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KCallable<*>, value: String) {
        println("$value has been assigned to '${property.name} in $thisRef.'")
    }
}
//common delegate property
var p2: String by Delegate()
open class Super
class Example: Super() {
    var p1: String by Delegate()
}

/*
对于一个委托给类实例的属性，如下
var prop: String by Delegate()
其在编译中，会产生如下的代码：
 */
class C: Super() {
    private val propdelegate = Delegate()
    var prop: String
        get() = propdelegate.getValue(this, this::prop)
        set(value: String) = propdelegate.setValue(this, this::prop, value)
}


//lazy delegate property ：首次访问才初始化
//val lazyValue: String by lazy {
/*
lazy参数 LazyThreadSafetyMode：
> 默认是 SYNCHRONIZED  同步
> PUBLICATION	发布。内部的计算过程可能被多次访问，但内部的属性是一个Volatile修饰的属性，所以在多线程环境中，
    被第一次访问获取数据后，此后的其它线程都共享该值。
> 未对多线程环境，做任何处理。
所以在多线程环境中使用，会造成：计算和返回值都可能处在多个线程中
 */
val lazyValue: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    println("computed!"  + Thread.currentThread().id)
    "Hello" + Thread.currentThread().id
}

/*
import kotlin.properties.Delegates
观察属性变化的委托
 */
class User {
    //observable() 返回Unit
    var name: String by Delegates.observable("<no name>") {
        prop, old, new ->
        println("$prop, $old -> $new")
    }

    //vetoable 否决， 返回Boolean,  true即否决
    var name2: String by Delegates.vetoable("<no name>") {
        prop, old, new ->
            println("$prop, $old -> $new")
        false
//        if (old == new) {
//            true
//        } else {
//            println("$prop, $old -> $new")
//            false
//        }
    }
}

//将属性委托给一个map
class Per(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

//Local Delegated Properties (since 1.1)
class Foo {
    fun isValid(): Boolean {
        return Random().nextBoolean()
    }

    fun doSomething() {
        println("doSomething")
    }
}
fun example(computeFoo: () -> Foo) {
    val memoizedFoo by lazy(computeFoo) //memoizedFoo: Foo
    if (memoizedFoo.isValid()) {
        memoizedFoo.doSomething()
    }

//    var p: String by Delegate() //可以有其它形式的委托属性
}

//委托类，可以实现两个接口 ReadOnlyProperty 、 ReadWriteProperty
class Example1: Super() {
    val p1: String by Delegate1()
}
class Delegate1: ReadOnlyProperty<Example1, String> {
    override fun getValue(thisRef: Example1, property: KProperty<*>): String {
        return ""
    }
}

/*
放置在by 右侧的是一个函数，它来创建一个实例对象
该对象所对应类中，使用 provideDelegate 来创建真正的委托实例
要求委托实例的类，要实现 ReadOnlyProperty 或 ReadWriteProperty
 */
class Dele(val id: String): ReadOnlyProperty<MyUI, String> {
    override fun getValue(thisRef: MyUI, property: KProperty<*>): String {
        if (id.equals("no_support")) {
            return id
        }
        return "${UUID.randomUUID()}_$id"
    }
}
class ResourceID {
    private constructor(id: String) {
        this.id = id
    }
    open val id: String
    companion object {
        val image_id = ResourceID("1")
        val text_id: ResourceID = ResourceID("2")
        val no_support_id: ResourceID = ResourceID("-1")
    }

}
class ResourceLoader(resId: ResourceID) {
    private val resourceID = resId

    operator fun provideDelegate(thisRef: MyUI, prop: KProperty<*>): ReadOnlyProperty<MyUI, String> {
        if (checkProperty(thisRef, prop.name)) {
            // create delegate
            return  Dele(resourceID.id+"")
        } else {
            // create delegate
            return  Dele("no_support")
        }
    }
    private fun checkProperty(thisRef: MyUI, name: String): Boolean {
        if (name.equals("none")) {
            //...
            return false
        }
        return true
    }
}
fun MyUI.bindResource(id: ResourceID): ResourceLoader {
    return ResourceLoader(id)
}
class MyUI {
    val image by bindResource(ResourceID.image_id) //bindResource()产生委托对象
    val text by bindResource(ResourceID.text_id)
    val none by bindResource(ResourceID.no_support_id)
}


fun main(args: Array<String>) {
    val example = Example()
    example.p1 = "stone1"
    println(example.p1)

    println()
    p2 = "stone2"
    println("定义在顶级的委托属性: $p2")

//    println(lazyValue)
//    println(lazyValue)

    println()
    (1..5).forEach {//多线程访问lazy委托属性
        Thread {
            println(lazyValue)
        }.start()
    }

    println()
    val user = User()
    user.name2 = "first"
    println(user.name2+"...")
    user.name2 = "second"

    println()
    val per = Per(mapOf("name" to "John Doe", "age" to 25))
    println(per.name)
    println(per.age)

    println()
    example {
        println("生成Foo")
        Foo()
    }

    println()
    println("image-id = " + MyUI().image)
    println("txt-id = " + MyUI().text)
    println("none-id = " + MyUI().none)
}