package io;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author ShaoJiale
 * Date: 2020/3/7
 */
public class NIODemo {
    public static void bioRead() {
        InputStream in = null;
        try {
            in = NIODemo.class.getClassLoader().getResourceAsStream("database.properties");
            byte[] buf = new byte[1024];
            int len;
            assert in != null;
            while ((len = in.read(buf)) != -1) {
                for (int i = 0; i < len; i++) {
                    System.out.print((char) buf[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void nioRead() {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile("src/resources/database.properties", "rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int len = channel.read(buffer);
            System.out.println(len);
            while (len != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                buffer.compact();
                len = channel.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test() {
        bioRead();
        System.out.println();
        nioRead();
    }
}
