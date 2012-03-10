package experiment.dynamicproxy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FooTest {
    @Test
    public void testDynamicProxy() {
        int id = 4;
        FooImpl foo = new FooImpl(id);
        Foo fooProxy = (Foo) DebugProxy.newInstance(foo);
        assertEquals(id, fooProxy.getId());
        assertEquals(foo.toString(), fooProxy.toString());
    }

    public interface Foo {
        int getId();
    }

    public static class FooImpl implements Foo {
        private final int id;

        public FooImpl(int id) {
            this.id = id;
        }

        @Override
        public int getId() {
            return id;
        }
    }
}
