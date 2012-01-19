package experiment.chunk;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author ycosmado
 * @since 1.0
 */
public class BufferedStreamTest extends TestSupport {

    @Test
    public void testReadWriteLineOverflow() throws Exception {
        testReadWriteLine(createSizedStreamFactory(2));
    }

    @Test
    public void testReadWriteLineDefault() throws Exception {
        testReadWriteLine(createDefaultStreamFactory());
    }

    private void testReadWriteLine(StreamFactory streamFactory) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MyBufferedOutputStream outputStream = streamFactory.createMyBufferedOutputStream(bos);
        String[] values = {
                "",
                "a",
                "bc",
                "def",
                "ghij",
                "klmno",
                "",
                "",
                "a",
                "",
                "bc",
                "",
                "def",
                "",
                "ghij",
                "",
                "klmno",
                "",
        };
        for (String value : values) {
            outputStream.writeLine(value);
        }
        byte[] bytes = bos.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        MyBufferedInputStream inputStream = streamFactory.createMyBufferedInputStream(bis);
        for (String value : values) {
            String value1 = inputStream.readLine();
            assertEquals(value, value1);
        }
    }

    @Test
    public void testReadWriteObjectOverflow() throws Exception {
        testReadWriteObject(createSizedStreamFactory(5));
    }

    @Test
    public void testReadWriteObjectDefault() throws Exception {
        testReadWriteObject(createDefaultStreamFactory());
    }

    private void testReadWriteObject(StreamFactory streamFactory) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MyBufferedOutputStream outputStream = streamFactory.createMyBufferedOutputStream(bos);
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "A");
        map.put(2, "B");
        Object[] values = {
                "",
                "a",
                "bc",
                "def",
                255,
                1234.67,
                map,
        };
        for (Object value : values) {
            outputStream.writeObject(value);
        }
        byte[] bytes = bos.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        MyBufferedInputStream inputStream = streamFactory.createMyBufferedInputStream(bis);
        for (Object value : values) {
            Object value1 = inputStream.readObject();
            assertEquals(value, value1);
        }
    }
    
    @Test
    public void testFoo() throws Exception {
        testFoo(createSizedStreamFactory(5));
    }

    private void testFoo(StreamFactory streamFactory) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MyBufferedOutputStream outputStream = streamFactory.createMyBufferedOutputStream(bos);

        byte[][] values = {
                "".getBytes(),
                "a".getBytes(),
                "bc".getBytes(),
                "def".getBytes(),
                "ghij".getBytes(),
                "klmno".getBytes(),
        };
        for (byte[] value : values) {
            OutputStream out = outputStream.getOutputStream();
            out.write(value, 0, value.length);
            out.close();
        }
        byte[] bytes = bos.toByteArray();

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        MyBufferedInputStream inputStream = streamFactory.createMyBufferedInputStream(bis);
        for (byte[] value : values) {
            byte[] value1 = inputStream.readBytes();
            assertTrue(Arrays.equals(value, value1));
        }
    }
}
