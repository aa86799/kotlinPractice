package com.stone.javacallkt;

import java.io.IOException;

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 15/06/2017 21 26
 */
public class Test {

    public static void callKot() {
        UnionKt.kta(); //in KtA.kt
        UnionKt.ktb(); //in KtB.kt

        CalcUtil.add(1, + 2); //in Common.kt
        int a = CalcUtil.const1;

        /*** in Common.kt ***/
        new Common().getProp1();
        new Common().setProp2("22aa");
        new Common().getProp2();
        new Common().prop3 = "33a";
        Common.Companion.getProp4();
        Common.Companion.getProp5();
        Common.Companion.setProp5(5000);
        Common.Companion.getProp6();
        Common.Companion.setProp6(668);
        a = Common.const3;
        Singleton.ps1 = 5;
        Singleton.ps2 = new Common();

        a = new Common().new InnerClass().prop8;
        a = new Common().new InnerClass().getProp9();
        a = Common.ObjClass.const4;
        a = ObjClass.const5;

        Common.staticMethod();
        Common.Companion.staticMethod();
        Common.ObjClass.staticMethod();
        ObjClass.staticMethod();

        Common.class.getFields();

        new Common().extensionFun();

        new Common().testF();
        new Common().f("");
        new Common().f("", 1);
        new Common().f("", 2, "");

        new Common().foo();
        try {
            new Common().foo2();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
