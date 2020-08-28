package com.stone.json

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * desc:    https://github.com/Kotlin/kotlinx.serialization
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2020/8/28 02:48
 */
fun main() {
    /* --------------------------- */
    @Serializable
    data class Project(var name: String, var language: String)

    val data = Project("kotlinx.serialization", "Kotlin")
    println(Json.encodeToString(data))

    val p: Project = Json.decodeFromString("""
       {"name":"kotlinx.serialization","language":"Kotlin"} 
    """)
    println(p)
}