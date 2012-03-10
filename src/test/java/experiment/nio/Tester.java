package experiment.nio;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.junit.Assert.assertEquals;

public class Tester {
    @Test
    public void testCreateArrayBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        char[] chars = {
                'a',
                'b',
                'c',
        };
        for (char c : chars) {
            buffer.put((byte) c);
        }

        buffer.flip();
        for (char c : chars) {
            char c1 = (char) buffer.get();
            System.out.println(c + " " + c1);
            assertEquals(c, c1);
        }
    }

    @Test
    public void testSliceBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            buffer.put((byte) i);
        }

        int sliceStart = 3;
        int sliceEnd = 7;
        buffer.position(sliceStart);
        buffer.limit(sliceEnd);

        ByteBuffer slice = buffer.slice();

        for (int i = 0; i < slice.capacity(); ++i) {
            byte b = slice.get(i);
            b *= 11;
            slice.put(i, b);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.remaining() > 0) {
            int pos = buffer.position();
            byte b = buffer.get();
            System.out.println(pos + " " + b);
            if (pos < sliceStart || pos >= sliceEnd) {
                assertEquals(pos, b);
            } else {
                assertEquals(pos * 11, b);
            }
        }
    }
    
    @Test
    public void testTypesInByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(64);
        int i = 30;
        long l = 7000000000000L;
        double d = Math.PI;

        buffer.putInt(i);
        buffer.putLong(l);
        buffer.putDouble(d);

        buffer.flip();

        assertEquals(i, buffer.getInt());
        assertEquals(l, buffer.getLong());
        assertEquals((int) (d * 100000), (int) (buffer.getDouble() * 100000));
    }

    @Test
    public void testUseFloatBuffer() {
        FloatBuffer buffer = FloatBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); ++i) {
            float f = (float) Math.sin((((float) i) / 10) * (2 * Math.PI));
            buffer.put(f);
        }

        buffer.flip();

        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }
}
