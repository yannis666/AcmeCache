package experiment.chunk;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class TestSupport {

    protected interface StreamFactory {
        MyBufferedInputStream createMyBufferedInputStream(InputStream in);
        MyBufferedOutputStream createMyBufferedOutputStream(OutputStream outputStream);
    }

    private class DefaultStreamFactory implements StreamFactory {

        @Override
        public MyBufferedInputStream createMyBufferedInputStream(InputStream in) {
            return new MyBufferedInputStream(in);
        }

        @Override
        public MyBufferedOutputStream createMyBufferedOutputStream(OutputStream outputStream) {
            return new MyBufferedOutputStream(outputStream);
        }
    }

    private class SizedStreamFactory implements StreamFactory {
        private final int chunkLength;

        private SizedStreamFactory(int chunkLength) {
            this.chunkLength = chunkLength;
        }

        @Override
        public MyBufferedInputStream createMyBufferedInputStream(InputStream in) {
            return new MyBufferedInputStream(in, new Chunk(chunkLength));
        }

        @Override
        public MyBufferedOutputStream createMyBufferedOutputStream(OutputStream outputStream) {
            return new MyBufferedOutputStream(outputStream, new Chunk(chunkLength));
        }
    }

    protected StreamFactory createDefaultStreamFactory() {
        return new DefaultStreamFactory();
    }

    protected StreamFactory createSizedStreamFactory(int chunkSize) {
        return new SizedStreamFactory(chunkSize);
    }
}
