package experiment.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class WriteSomeBytes {
    static private final byte message[] = "Some bytes.".getBytes();

    static public void main(String args[]) throws Exception {
        File f = new File("writesomebytes.txt");
        System.out.println(f.getAbsoluteFile());
        FileOutputStream fout = new FileOutputStream(f);
        FileChannel fc = fout.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        for (byte b : message) {
            buffer.put(b);
        }
        buffer.flip();
        fc.write(buffer);
        fout.close();
    }
}
