package com.stone.clazzobj.enums

/**
 * desc  : 枚举操作
 * author: stone
 * email : aa86799@163.com
 * time  : 07/07/2017 11 15
 */

enum class Direction { NORTH, SOUTH, WEST, EAST
}

enum class Color(val rgb: Int) {
    RED(0xFF0000), GREEN(0x00FF00), BLUE(0x0000FF);
}

enum class ProtocolState {//含抽象方法
    WAITING {
        override fun signal() = TALKING
    },
    TALKING {
        override fun signal() = WAITING
    },

    Test {
        override fun signal(): ProtocolState {
            return WAITING
        }
    };

    abstract fun signal(): ProtocolState
}

enum class RestrictUserType(var flag: Boolean?) {
    C_USER(false),
    B_USER(false),
    BOTH(false);
}

fun main(args: Array<String>) {
    var direction = Direction.EAST
    direction = Direction.valueOf("NORTH")
    val ary: Array<Direction> = Direction.values()
    for (d in ary) {
        println("name=${d.name}, ordinal=${d.ordinal}")
    }

    Color.values().forEach {
        println("name=${it.name}, ordinal=${it.ordinal}, ${it.rgb}")
    }
    //kotlin1.1 新增的操作函数 enumValues<EnumClass>()  <==> EnumClass.values()
    enumValues<Color>().forEach {  }
    //kotlin1.1 新增的操作函数 enumValueOf<EnumClass>(String)  <==> EnumClass.valueOf(String)
    val colorRgb = enumValueOf<Color>("RED").rgb
    println(colorRgb)
    println(0xFF0000)

    val type = RestrictUserType.C_USER.apply { flag = true }
    if (type == RestrictUserType.C_USER && RestrictUserType.C_USER.flag!!) {
        println("${type.flag}")
    }

}