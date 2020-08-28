package com.stone.idioms.pojo

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 27/05/2017 17 25
 */
//data class Customer(val name: String, val email: String)  //var val 关系到外部使用时  属性能否 重新指定值
data class Customer(var name: String, var email: String)    //
data class User(var name: String = "", val email: String = "") //为属性添加默认值  之后创建对象时，就可以不输或少输构造方法参数
/*
data class 会 自动生成一个POJO， 含有如下functions:
    -- getters (当参数使用var定义时，还会生成setters) for all properties
    -- equals()
    -- hashCode()
    -- toString()
    -- copy()
    -- component
 */

/*class Customer {

    var name: String
    var email: String = "default@"

    constructor(name: String, email: String) {
        this.name = name
        this.email = email
    }

    constructor(name: String) {
        this.name = name
    }

//    fun getName():String { //不能创建
//        return name
//    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Customer

        if (name != other.name) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "Customer(name='$name', email='$email')"
    }

}*/

fun main(args: Array<String>) {
    var customer = Customer("a", "a@gmail.com")
//    customer.name = "b"
//    customer.email = "b@163.com"
    println("name : ${customer.name} , email : ${customer.email}")

//    customer = Customer("c")
//    println("name : ${customer.name} , email : ${customer.email}")

    var user = User()
    user = User("admin")
    user = User("admin", "@znds.com")

    //直接调用java-class： User2
    var user2 = User2("stone")

}