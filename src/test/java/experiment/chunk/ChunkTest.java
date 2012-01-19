package experiment.chunk;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author ycosmado
 * @since 1.0
 */
public class ChunkTest {
    @Test
    public void testLengthBad() {
        int maxLength = 256;
        Chunk chunk = new Chunk(maxLength);

        int[] badValues = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE / 2,
                -1,
                0,
                maxLength + 1,
                maxLength + 5,
        };
        for (int length : badValues) {
            try {
                chunk.setLength(length);
                fail();
            } catch (IOException e) {
                //good
            }
        }
    }

    @Test
    public void testLengthGood() throws Exception {
        int maxLength = 4096;
        Chunk chunk = new Chunk(maxLength);
        for (int i=1; i<maxLength; i++) {
            chunk.setLength(i);
            assertEquals(i, chunk.getLength());
        }
    }

    @Test
    public void testConstructorBad() {
        int[] badValues = {
                Integer.MIN_VALUE,
                Integer.MIN_VALUE / 2,
                -1,
                0,
                0xFFFF + 1,
                0xFFFF + 5,
                Integer.MAX_VALUE,
        };
        for (int length : badValues) {
            try {
                new Chunk(length);
                fail();
            } catch (IllegalArgumentException e) {
                // good
            }
        }
    }

    @Test
    public void testConstructorGood() {
        int[] goodValues = {
                1,
                0xFFFF / 2,
                0xFFFF - 1,
                0xFFFF,
        };
        for (int length : goodValues) {
            new Chunk(length);
        }
    }
    
    @Test
    public void testAtEnd(){
        int length = 5;
        Chunk chunk = new Chunk(length);
        for (int i=0; i < 5; i++) {
            assertFalse("i=" + i, chunk.atEnd());
            chunk.write(i);
        }
        assertTrue(chunk.atEnd());
    }
}
