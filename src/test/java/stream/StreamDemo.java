package stream;

import java.util.Arrays;
import java.util.Random;

public class StreamDemo {

    public static void main(String[] args) {
        int[] ar1 = {1, 2, 3, 4, 5};
        Arrays.stream(ar1).filter(i -> {
            int trans = i * 10 + new Random().nextInt();
            System.out.print("trans " + trans + "   ");
            if (trans % 2 == 0) {
                return true;
            } else {
                return false;
            }
        }).forEach( i -> {
            System.out.println("stream " + i); // trans 为偶数时  输出
        });

    }
}
