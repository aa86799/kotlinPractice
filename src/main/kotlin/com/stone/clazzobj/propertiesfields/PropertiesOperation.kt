package com.stone.clazzobj.propertiesfields

//import kotlin.reflect.jvm.internal.impl.javax.inject.Inject

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 10 21
 */

//常量 必须定义在 Kotlin文件顶级， 不能定在 class或其它内部(在内部就是二级了)
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
@Deprecated(SUBSYSTEM_DEPRECATED) fun foo() { }

class PropertiesOperation(size: Int) {

    val size = size

    //var 可变，可重写 get()、set()   仅指定get()时，需要进行初始化，指定默认值； set()为默认实现
    var stringRepresentation1: String = ""
        get() = this.toString()

    //var 重写 get()、set()
    var stringRepresentation2: String
        get() = this.toString()
        set(value) {
            setDataFromString(value) // parses the string and assigns values to other properties
        }

    private fun  setDataFromString(value: String) {}

    //val 不可变，只能重写 get()
    val isEmpty1: Boolean get() = this.size == 0
    val isEmpty2 get() = this.size == 0 // has type Boolean


    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation
    var setterWithAnnotation: Any? = null
        /*@Inject*/ set // annotate the setter with Inject

    /*
    使用 field 关键字，指代它所在的属性。它仅能在 属性的 get或set中使用
    还有个比较形象的称呼：backing field。  向后看，就知它是指哪个属性了
     */
    var counter = 0 // the initializer value is written directly to the backing field
        set(value) {
            if (value >= 0) field = value
        }

    /*
    backing property
    使用一个私有属性 来实现当前属性的get、set
     */
    private var _table: Map<String, Int>? = null
    val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap() // Type parameters are inferred }
                return _table ?: throw AssertionError("Set to null by another thread")
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }


}

object Tes {//singleton

    //针对var 属性， 延迟初始化
    lateinit var str: String

    fun init(arg: String) {
        str = arg
    }


    //针对val 属性， 延迟加载
    val name: String by lazy {
        println("load name")
        "stone86"
    }
}


fun main(args: Array<String>) {
    val gs = PropertiesOperation(3)
//    gs.setterVisibility = "" //set是private的 无法访问

    Tes.init("stone")
    println(Tes.str)
    println(Tes.name)

}