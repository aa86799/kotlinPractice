package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *	Exchanger： 两个线程  间 交换 数据
 *		exchange() 等待另一个线程到达此交换点（除非当前线程被中断），
 *					然后将给定的对象传送给该线程，并接收该线程传来的对象。
 *    即：  当线程为单数条时，那么总有条线程是交换不到数据的，该线程一直等待。。
 *
 *    设计 一个小游戏：相亲随机配对
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final Exchanger<String> exchanger = new Exchanger<String>();
        final List<String> males = new ArrayList<String>();
        males.add("张三");
        males.add("李四");
        males.add("王五");
        males.add("赵六");
        males.add("卓七");
        final List<String> females = new ArrayList<String>();
        females.add("张晓");
        females.add("李丽");
        females.add("王梅");
        females.add("赵雅");
        females.add("卓红");
        females.add("郑月");
        final Random random = new Random();
        final int nums = 5;

        Runnable r1 = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < nums; i++) {
                    final int j = random.nextInt(nums - i);
                    try {
                        String data1 = males.get(j);
                        System.out.println(Thread.currentThread().getName()
                                + "正在把 ：'" + data1 + "'提出速配");
                        Thread.sleep((long) (Math.random() * 1000));

                        String data2 = exchanger.exchange(data1);
                        System.out.println(Thread.currentThread().getName()
                                + "速配成功：'" + data1 + "'----" + "'" + data2+ "'");
                        males.remove(j);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        service.execute(r1);

        Runnable r2 = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < nums; i++) {
                    final int j = random.nextInt(nums - i);
                    try {
                        String data1 = females.get(j);
                        System.out.println(Thread.currentThread().getName()
                                + "正在把 ：'" + data1 + "'提出速配");
                        Thread.sleep((long) (Math.random() * 1000));

                        String data2 = exchanger.exchange(data1);
					/*	System.out.println(Thread.currentThread().getName()
								+ "速配成功：'" + data1 + "'----" + "'" + data2+ "'");*/
                        females.remove(j);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        service.execute(r2);

        service.shutdown();
    }
}