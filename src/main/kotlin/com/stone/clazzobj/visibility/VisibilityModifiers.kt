package com.stone.clazzobj.visibility

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 01/06/2017 15 06
 */
/*
Classes, objects, interfaces, constructors, functions, properties and their setters can have visibility modifiers.
Getters always have the same visibility as the property.
There are four visibility modifiers in Kotlin: private , protected , internal and public .
The default visibility, used if there is no explicit modifier, is public .
 */

fun baz() {} //default public, it will be visible everywhere
private class Bar {}  //private , it will only be visible inside the file containing the declaration
internal fun Bas() {} //internal , it is visible everywhere in the same module(an IntelliJ IDEA module; a Maven or Gradle project; a set of files compiled with one invocation of the Ant task)
//protected class Bz  //protected is not available for top-level declarations.

class Human {//or interface
//    — private     // this class only
//    — protected   // same as private + visible in subclasses too
//    — internal    // 在module中 任何能访问该类的client
//    — public      // 任何能访问该类的client
}

class C private constructor(a: Int) {} //the primary constructor visible this class only
internal class D /*internal or public*/ constructor(a: Int) {} //constructor: visible internal

fun local() {
//    public val c = 10
    /*
    Local variables, 不具有可见性修饰语
     */
}
