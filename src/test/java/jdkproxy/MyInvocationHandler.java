package jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyInvocationHandler implements InvocationHandler {

    private Object mTarget;

    MyInvocationHandler(Object target) {
        mTarget = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("-----before------"+ method.getName() +"------------");

        Object result = method.invoke(mTarget, args);

        // 在目标对象的方法执行之后简单的打印一下
        System.out.println("-----after-------"+ method.getName() +"-----------");
        return result;
    }
}

interface IMyBiz1 {
    void doSth1();
}

interface IMyBiz2 {
    void doSth2();
}

class MyBizImpl implements IMyBiz1, IMyBiz2 {

    @Override
    public void doSth1() {
        System.out.println("do some thing 1");
    }

    @Override
    public void doSth2() {
        System.out.println("do some thing 2");
    }
}

class TestJdkProxy {
    public static void main(String[] args) {
        MyBizImpl biz = new MyBizImpl();
        // Thread.currentThread().getContextClassLoader()
        Object proxy = Proxy.newProxyInstance(TestJdkProxy.class.getClassLoader(),
                biz.getClass().getInterfaces(), new MyInvocationHandler(biz));
        ((IMyBiz1)proxy).doSth1();
        ((IMyBiz2)proxy).doSth2();
    }
}