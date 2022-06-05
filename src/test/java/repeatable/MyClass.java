package repeatable;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * desc:
 * author:  stone
 * email:   aa86799@163.com
 * blog :   https://stone.blog.csdn.net
 * time:    2022/6/5 14:09
 */
public class MyClass {

    // 负责生产的角色
    @Employee(value = {@Role(RoleType.Worker), @Role(RoleType.Engineer)})
    public void produce() {

    }

    // 负责管理的角色
    // Employee 不能与被它包含的元素 @Role 同时使用
//    @Employee(value = {@Role(RoleType.Manager), @Role(RoleType.Master), @Role(RoleType.CEO), @Role(RoleType.President)})
    @Role(RoleType.Manager)
    @Role(RoleType.Master)
    @Role(RoleType.CEO)
    @Role(RoleType.President)
    public void manage() {

    }

    // 查看工时操作，对应的角色
    @Role(RoleType.All)
    public void checkWorkingHours() {

    }

    public static void main(String[] args) throws NoSuchMethodException {
        Class<MyClass> clz = MyClass.class;
        Method produce = clz.getMethod("produce");
        Employee employee = produce.getAnnotation(Employee.class);
        System.out.println(employee); // @repeatable.Employee(value=[@repeatable.Role(value=Worker), @repeatable.Role(value=Engineer)])
        Role[] roles = employee.value();
        System.out.println(Arrays.toString(roles));
        System.out.println("-----------------");

        /* 虽然在 manage() 方法没有使用 @Employee， 但仍可以get该注解。猜想可能编译后是一样的 */
        Method manage = clz.getMethod("manage");
        Employee manageEmployee = manage.getAnnotation(Employee.class);
        System.out.println(manageEmployee); // @repeatable.Employee(value=[M..,M..,C..,P..])
        Role[] manageRoles = manageEmployee.value();
        System.out.println(Arrays.toString(manageRoles));
        System.out.println(manage.getDeclaredAnnotations().length); // 输出1，而不是4
    }
}
