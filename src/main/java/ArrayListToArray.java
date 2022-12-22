import java.util.ArrayList;

public class ArrayListToArray {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();

        list.add("C");
        list.add("C++");
        list.add("Java");
        list.add("Android");

        String str[] = new String[list.size()];
//        str=list.toArray(str);
        str = list.toArray(new String[0]);

        for (int i = 0; i < str.length; ++i) {
            System.out.println(str[i] + " ");
        }

    }
}
