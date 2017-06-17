package com.github.gino0631.common.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

/**
 * Utilities for working with files.
 */
public final class IoFiles {
    private IoFiles() {
    }

    public static Path createTempFile(String prefix) throws IOException {
        Path file = Files.createTempFile(prefix, null);
        file.toFile().deleteOnExit();

        return file;
    }

    public static void delete(Path file, Consumer<IOException> errorHandler) {
        if (file != null) {
            try {
                Files.delete(file);

            } catch (IOException e) {
                if (errorHandler != null) {
                    errorHandler.accept(e);
                }
            }
        }
    }
}
