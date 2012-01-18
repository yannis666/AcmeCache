package experiment.chunk;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChunkTest {
    @Test
    public void testLengthBad() {
        int maxLength = 256;
        Chunk chunk = createChunkHelper(maxLength);

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
        Chunk chunk = createChunkHelper(maxLength);
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
                createChunkHelper(length);
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
            createChunkHelper(length);
        }
    }

    private Chunk createChunkHelper(int length) {
        return new Chunk(length);
    }
}
