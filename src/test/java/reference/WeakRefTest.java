package reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/4/30 11:36
 */
public class WeakRefTest {
    public static RefTestData alive;
    // 仅有一个弱引用, 则gc 会回收此弱引用对象。
    private WeakReference<RefTestData> weak;

    private void test() {
        RefTestData data = new RefTestData("stone");
        weak = new WeakReference<>(data);
        data = null;
        System.gc();
        // 测试后，发现在gc后，再次使用，无法从弱引用中得到原对象了，其为 null
        System.out.println("data = " + (weak.get()));
    }

    private void testQueue1() {
        ReferenceQueue<RefTestData> queue = new ReferenceQueue<>();
        RefTestData data = new RefTestData("stone");
        weak = new WeakReference<>(data, queue);
        System.gc();
        // new RefTestData("stone"), 它有两个引用，一个 data的强引用；一个weak的弱引用；所以gc不能回收
        System.out.println("data0 = " + (weak.get()));
        data = null;
        System.gc();
        // 断开了data的强引用，只有一个弱引用，会回收，返回null
        System.out.println("data1 = " + (weak.get()));
        // 轮询该队列以查看引用对象是否可用。如果有一个是可用的，且没有进一步的延迟，那么它将从队列中删除并返回。否则，该方法立即返回null。
        Reference<? extends RefTestData> ref = queue.poll();
        System.out.println("ref = " + ref); // queue.poll() 成功了
        System.out.println("data2 = " + ref.get()); // 但原对象，若已被回收了；get() 返回null

        try {
            Thread.sleep(2000);
            System.out.println("data3 = " + alive);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void testQueue2() {
        ReferenceQueue<RefTestData> queue = new ReferenceQueue<>();
        RefTestData data = new RefTestData("stone");
        weak = new WeakReference<>(data, queue);
        System.gc();
        // new RefTestData("stone"), 它有两个引用，一个 data的强引用；一个weak的弱引用；所以gc不能回收
        System.out.println("data0 = " + (weak.get()));
        data = null; // 如果不断开强引用，后续的 queue.remove() 将一直阻塞
        System.gc();
        // 断开了data的强引用，只有一个弱引用，会回收，返回null
        System.out.println("data1 = " + (weak.get()));
        try {
            Reference<? extends RefTestData> ref = queue.remove();
            System.out.println("ref = " + ref); // queue.remove() 成功了
            System.out.println("data2 = " + ref.get()); // 但原对象，若已被回收了；get() 返回null
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WeakRefTest refTest = new WeakRefTest();
//        refTest.test();
        refTest.testQueue1();
        System.out.println("-----------------");
        refTest.testQueue2();

    }
}
