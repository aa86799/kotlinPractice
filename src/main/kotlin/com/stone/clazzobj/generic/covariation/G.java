package com.stone.clazzobj.generic.covariation;


import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

//public class G<? extends K> {
//public class G<? Super K> {
//public class G<? Super ChildClass> {
//public class G<? extends ChildClass> {
//public class G<T super ChildClass> {
public class G<T extends SuperClass> {

    void addAll(G<? extends T> items) {
//        items = gg;
//        gg = items;
    }

    void copyAll(G<SuperClass> to, G<ChildClass> from) {
        to.addAll(from);
    }

    public static void main(String[] args) {
        ArrayList<? extends SuperClass> list = new ArrayList<ChildClass>();
//        list.add(new SuperClass())
//        list.add(new ChildClass())

        new G<SuperClass>().addAll(new G<ChildClass>());

        String[] iAry = new String[] {"abc"}; //这是不变的
        Object[] objects = iAry; //这里是协变的
        objects[0] = 3; //能编译，运行报错了
    }

    <X>  X find(List<? extends X> list, int index) {
        return list.get(index);
    }

    <K> K  update(K t) {
        return t;
    }


}
