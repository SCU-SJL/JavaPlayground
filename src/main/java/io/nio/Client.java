package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ShaoJiale
 * Date: 2020/4/2
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = null;

        try {
            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(8000));

            ByteBuffer writeBuf = ByteBuffer.allocate(32);
            ByteBuffer readBuf = ByteBuffer.allocate(32);

            while (true) {
                writeBuf.put("hello3".getBytes());
                writeBuf.flip();
                socketChannel.write(writeBuf);
                writeBuf.rewind();
                readBuf.clear();
                socketChannel.read(readBuf);
                System.out.println(new String(readBuf.array()));
                Thread.sleep(3000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                socketChannel.close();
            }
        }
    }
}
