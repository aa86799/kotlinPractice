package reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/4/30 11:36
 */
public class PhantomRefTest {

    // 虚不可达，get()总是为null。任何时候都可能回收。
    // 只有一条虚引用，且重写了finalize()，不会入队，queue.poll()返回null，queue.remove()阻塞。
    // 只有一条虚引用，且未重写finalize()，会入队，queue.poll()、remove()返回一个Reference<T>。
    private PhantomReference<RefTestData> phantom;

    private void testQueue1() {
        ReferenceQueue<RefTestData> queue = new ReferenceQueue<>();
        RefTestData data = new RefTestData("stone");
        phantom = new PhantomReference<>(data, queue);
        // 虚不可达，总是返回null
        System.out.println("data0 = " + (phantom.get()));
        data = null;
        System.gc();
        // 虚不可达，总是返回null
        System.out.println("data1 = " + (phantom.get()));
        // 轮询该队列以查看引用对象是否可用。如果有一个是可用的，且没有进一步的延迟，那么它将从队列中删除并返回。否则，该方法立即返回null。
        Reference<RefTestData> ref = (Reference<RefTestData>) queue.poll();
        System.out.println("ref = " + ref); // 当重写了finalize()后，这里会返回 null
//        System.out.println("data2 = " + ref.get()); // 但原对象，若已被回收了；get() 返回null
    }

    private void testQueue2() {
        ReferenceQueue<RefTestData> queue = new ReferenceQueue<>();
        RefTestData data = new RefTestData("stone");
        phantom = new PhantomReference<>(data, queue);
        System.gc();
        // 虚不可达，总是返回null
        System.out.println("data0 = " + (phantom.get()));
        data = null; // 如果不断开强引用，后续的 queue.remove() 将一直阻塞
        System.gc();
        // 虚不可达，总是返回null
        System.out.println("data1 = " + (phantom.get()));
        try {
            Reference<? extends RefTestData> ref = queue.remove();
            System.out.println("ref = " + ref); // 当重写了finalize()后，这里会返回 null
//            System.out.println("data2 = " + ref.get()); // 但原对象，若已被回收了；get() 返回null
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PhantomRefTest refTest = new PhantomRefTest();
        refTest.testQueue1();
        System.out.println("-----------------");
        refTest.testQueue2();
    }
}
