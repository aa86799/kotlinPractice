package repeatable;

import java.lang.annotation.*;

//@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.RUNTIME)
//public @interface Values {
//    MyRepeatable[] value();
//}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Employee { // 一个员工，可以有多个角色
    Role[] value(); // 当本注解，被@Repeatable使用后，则必须实现 返回一个数组，类型为 被@Repeatable 注解的注解
}