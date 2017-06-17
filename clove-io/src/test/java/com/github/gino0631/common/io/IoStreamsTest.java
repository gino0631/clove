package com.github.gino0631.common.io;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

public class IoStreamsTest {
    @Test
    public void testLimit() throws Exception {
        InputStream is = IoStreams.limit(new ByteArrayInputStream(new byte[100]), 15);
        assertEquals(15, IoStreams.exhaust(is));
        assertEquals(0, IoStreams.exhaust(is));
    }

    @Test
    public void testCount() throws Exception {
        AtomicLong inputCounter = new AtomicLong();
        AtomicLong outputCounter = new AtomicLong();

        InputStream is = IoStreams.count(new ByteArrayInputStream(new byte[15]), inputCounter::addAndGet);
        OutputStream os = IoStreams.count(new ByteArrayOutputStream(), outputCounter::addAndGet);

        is.mark(100);
        IoStreams.copy(is, os);
        is.reset();
        IoStreams.copy(is, os);

        assertEquals(15, inputCounter.intValue());
        assertEquals(30, outputCounter.intValue());
    }

    @Test
    public void testWaste() throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[15]);
        assertEquals(3, IoStreams.waste(is, 3));
        assertEquals(12, IoStreams.waste(is, 100));
        assertEquals(0, IoStreams.waste(is, 1));
    }

    @Test
    public void testExhaust() throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(new byte[15]);
        assertEquals(15, IoStreams.exhaust(is));
        assertEquals(0, IoStreams.exhaust(is));
    }

    @Test
    public void testCopy() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(100);
        try (OutputStream os = baos) {
            assertEquals(15, IoStreams.copy(new ByteArrayInputStream(new byte[15]), os));
        }

        assertEquals(15, baos.size());
    }
}
