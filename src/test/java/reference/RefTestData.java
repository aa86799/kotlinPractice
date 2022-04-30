package reference;

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/4/30 11:29
 */
public class RefTestData {

    String name;

    RefTestData(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RefTestData{" +
                "name='" + name + '\'' +
                '}';
    }

    /**
     * 只会在对象内存回收前被调用一次。不确保一定能执行完全(比如内部写了耗时逻辑)。
     * 但可重新激活this引用
     */
    @Override
    public void finalize() {
        System.out.println("执行了 finalize()");
        WeakRefTest.alive = this;
    }
}
