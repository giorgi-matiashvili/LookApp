package com.lookapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Created by Giorgi on 6/20/2015.
 */
public class IOUtils {
    private static final int BUFFER_SIZE = 1024;
    private static final String DEFAULT_TEXT_ENCODING = "UTF-8";


    public static String toString(InputStream is, String encoding) throws IOException {
        return new String(toByteArray(is), encoding);
    }

    public static String toString(InputStream is) throws IOException {
        return toString(is, DEFAULT_TEXT_ENCODING);
    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        byte[] buff = new byte[BUFFER_SIZE];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        while (true) {
            int read = is.read(buff);
            if (read == -1)
                break;
            os.write(buff, 0, read);
        }
        return os.toByteArray();
    }

    public static void closeQuietly(InputStream is) {
        try {
            is.close();
        } catch (Exception e) {
        }
    }


    public static void closeQuietly(OutputStream os) {
        try {
            os.close();
        } catch (Exception e) {
        }
    }

    public static void closeQuietly(Writer wr) {
        try {
            wr.close();
        } catch (Exception e) {
        }
    }

}