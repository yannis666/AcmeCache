package experiment.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * from <a href="http://docs.oracle.com/javase/1.3/docs/guide/reflection/proxy.html">http://docs.oracle.com/javase/1.3/docs/guide/reflection/proxy.html</a>
 */
public class DebugProxy implements InvocationHandler {
    private Object obj;

    private DebugProxy(Object obj) {
        this.obj = obj;
    }

    public static Object newInstance(Object obj) {
        return java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces(),
                new DebugProxy(obj));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result;
        try {
            System.out.println("----- before method: " + method.getName());
            result = method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            throw new RuntimeException("unexpected exception: " + e.getMessage());
        } finally {
            System.out.println("----- after method: " + method.getName());
        }
        return result;
    }
}
