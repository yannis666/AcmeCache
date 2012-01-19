package experiment.chunk;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author ycosmado
 * @since 1.0
 */
public class BufferedStreamTest {

    @Test
    public void testReadWriteLine() throws Exception {
        int chunkLength = 2;
        Chunk chunk = new Chunk(chunkLength);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MyBufferedOutputStream outputStream = new MyBufferedOutputStream(bos, chunk);
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
        MyBufferedInputStream inputStream = new MyBufferedInputStream(bis, chunk);
        for (String value : values) {
            String value1 = inputStream.readLine();
            assertEquals(value, value1);
        }
    }

    @Test
    public void testReadWriteObject() throws Exception{
        int chunkLength = 2;
        Chunk chunk = new Chunk(chunkLength);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MyBufferedOutputStream outputStream = new MyBufferedOutputStream(bos, chunk);
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
        MyBufferedInputStream inputStream = new MyBufferedInputStream(bis, chunk);
        for (Object value : values) {
            Object value1 = inputStream.readObject();
            assertEquals(value, value1);
        }
    }
}
