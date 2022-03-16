import java.util.function.Supplier;

public class ClzJava {
    public static int AA = 10;

    public static void main(String[] args) {
//        ThreadLocal<Integer> tl = new ThreadLocal<>();
        ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 100); // 有初始值
        tl.set(123);

        new Thread("t1") {
            @Override
            public void run() {
                System.out.println(this.getName()+"-get" + tl.get()); // 没有初始值返回 null，若有就返回初始值; 主线程 set，不影响当前线程的 内部 map 集合
                try {
                    sleep(500);
                    tl.set(456);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread("t2") {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    System.out.println(this.getName()+"-get" + tl.get()); // 没有初始值返回 null，若有就返回初始值; 主线程 set，不影响当前线程的 内部 map 集合
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
