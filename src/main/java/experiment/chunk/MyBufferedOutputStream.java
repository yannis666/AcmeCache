/*
 *
 * Copyright (c) 2012. All Rights Reserved. Oracle Corporation.
 *
 * Oracle is a registered trademark of Oracle Corporation and/or its affiliates.
 *
 * This software is the confidential and proprietary information of Oracle
 * Corporation. You shall not disclose such confidential and proprietary
 * information and shall use it only in accordance with the terms of the license
 * agreement you entered into with Oracle Corporation.
 *
 * Oracle Corporation makes no representations or warranties about the
 * suitability of the software, either express or implied, including but not
 * limited to the implied warranties of merchantability, fitness for a
 * particular purpose, or non-infringement. Oracle Corporation shall not be
 * liable for any damages suffered by licensee as a result of using, modifying
 * or distributing this software or its derivatives.
 *
 * This notice may not be removed or altered.
 */
package experiment.chunk;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ycosmado
 * @since 1.0
 */
public class MyBufferedOutputStream extends BufferedOutputStream {
    private final Chunk chunk;

    public MyBufferedOutputStream(OutputStream outputStream, Chunk chunk) {
        super(outputStream);
        if (chunk == null) {
            throw new NullPointerException();
        }
        this.chunk = chunk;
    }
    
    public void writeLine(String line) throws IOException {
        byte[] bytes = line.getBytes();
        writeBytes(bytes, 0, bytes.length);
        flush();
    }
    
    private void writeBytes(byte[] bytes, int start, int length) throws IOException {
        chunk.reset();
        for (int i=0; i < start + length; i++) {
            if (chunk.atEnd()) {
                chunk.write(this);
                chunk.reset();
            }
            chunk.write(bytes[i]);
        }
        chunk.write(this);
        // TODO: currently, after each write we add a "next chunk length"
        // TODO: could eliminate this if lead contained whether there was continuation
        chunk.reset();
        chunk.write(this);
    }
}
