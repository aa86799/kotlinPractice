package repeatable;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Employee.class)
// 自定义的 可重复应用的 注解
public @interface Role {
    RoleType value() default RoleType.Worker; // 默认是工人
}

enum RoleType {
    Worker, Manager, Engineer, Master, CEO, President, All
}