package domain;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ExperimentalTest {


    private static String TEST_CLASS_NAME = "domain.Zoo";

    @Test
    public void foo() throws Exception {
        //TODO: need to add the following jar to the classpath
        // and test serialization/deserialization
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        ClassLoader cl1 = new MyClassLoader(getDomainJarURIs(), cl);
        ClassLoader cl2 = new MyClassLoader(getDomainJarURIs(), cl);

        //Object o1 = Class.forName(TEST_CLASS_NAME, false, cl1).newInstance();
        Class clazz = cl1.loadClass(TEST_CLASS_NAME);
        Object o1 = clazz.newInstance();

        byte[] bytes = toBytes(o1, cl1);
        Object o2 = fromBytes(bytes, cl1);
//
//        Cache cache1 = factory.getCacheManager(cl1).createCacheBuilder("c1").build();
//        Cache cache2 = factory.getCacheManager(cl2).createCacheBuilder("c2").build();
//
//        cache1.put(1, o1);
//        Object o1_1 = cache1.get(1);
//        assertSame(o1.getClass(), cache1.get(1));
//        cache2.put(1, o1);
//        assertNotSame(o1.getClass(), cache2.get(1));
    }


    private byte[] toBytes(Object value, ClassLoader classLoader) throws IOException {
        ClassLoader oldClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(classLoader);
        try {
            return toBytes(value);
        } finally {
            Thread.currentThread().setContextClassLoader(oldClassLoader);
        }
    }

    private byte[] toBytes(Object value) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            bos.flush();
            return bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                // eat this up
            }
        }
    }

    private Object fromBytes(byte[] bytes, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;
        try {
            ois = new MyObjectInputStream(bos, classLoader);
            return (Object) ois.readObject();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                // eat this up
            }
        }
    }
    private URL[] getDomainJarURIs() throws MalformedURLException {
        String domainJar = "C:/Users/yannis/IdeaProjects/jsr107/jsr107tck/implementation-tester/target/domainlib/domain.jar";
        return new URL[]{new File(domainJar).toURI().toURL()};
    }

    // utilities --------------------------------------------------------------

    private static class MyClassLoader extends URLClassLoader {
        public MyClassLoader(ClassLoader parent) {
            this(new URL[0], parent);
        }

        public MyClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println("find class name=" + name + ", this=" + this);
            return super.findClass(name);
        }
    }

    private static class MyObjectInputStream extends ObjectInputStream {
        private final ClassLoader classloader;

        public MyObjectInputStream(InputStream in, ClassLoader classloader) throws IOException {
            super(in);
            this.classloader = classloader;
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            String name = desc.getName();
            try {
                return Class.forName(name, false, classloader);
            } catch (ClassNotFoundException ex) {
                return super.resolveClass(desc);
            }
        }
    }
}
