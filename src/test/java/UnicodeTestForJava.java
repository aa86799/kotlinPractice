import org.junit.Test;


public class UnicodeTestForJava {

    @Test
    public void test() {
        char charSymbol = '厂';
        String unicode = Integer.toHexString(charSymbol); // => 16 进制
        System.out.println("\\u" + unicode);
        System.out.println(Integer.parseInt(unicode, 16)); // => 10进制
    }
}
