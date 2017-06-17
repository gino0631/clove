package com.github.gino0631.common.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An entity that can be written to an OutputStream.
 */
public interface Writable {
    void writeTo(OutputStream out) throws IOException;
}
