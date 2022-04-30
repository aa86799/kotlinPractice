package reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/4/30 11:28
 */
public class SoftRefTest {

    // 仅有一个软引用，内存充足时，不会回收
    private SoftReference<RefTestData> soft;

    private void test() {
        RefTestData data = new RefTestData("stone");
        soft = new SoftReference<>(data);
        data = null;
        try {
        System.gc();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 测试后，发现在短时间内，即使置空了 data，在软引用中还是可以获得
        System.out.println("data = " + (soft.get()));
    }

    private void testQueue() {
        RefTestData data = new RefTestData("stone");
        ReferenceQueue<RefTestData> queue = new ReferenceQueue<>();
        soft = new SoftReference<>(data, queue);
        data = null;
        try {
            System.gc();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 测试后，即使置空了 data，在软引用中还是可以获得
        System.out.println("data1 = " + (soft.get()));
        if (queue.poll() != null) {
            // 由于在软引用中可以get， 所以未加入到引用队列中； 以下方法不会执行
            System.out.println("data2 = " + (queue.poll().get()));
        }
        try {
            // 由于未加入引用队列，引用队列为空，会一直阻塞
            System.out.println("data3 = " + (queue.remove().get()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SoftRefTest softRefTest = new SoftRefTest();
//        softRefTest.test();
        softRefTest.testQueue();
    }
}
