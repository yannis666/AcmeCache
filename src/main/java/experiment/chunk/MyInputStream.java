package experiment.chunk;

import java.io.FilterInputStream;
import java.io.InputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyInputStream extends FilterInputStream {
    protected MyInputStream(InputStream in) {
        super(in);
    }
}
