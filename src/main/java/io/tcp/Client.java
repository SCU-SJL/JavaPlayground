package io.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

/**
 * @author ShaoJiale
 * Date: 2020/3/7
 */
public class Client {
    private ByteBuffer buffer = ByteBuffer.allocate(1024);
    private SocketChannel socketChannel;

    public void run() {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));

            if (socketChannel.finishConnect()) {
                int i = 0;
                while (i < 10) {
                    TimeUnit.SECONDS.sleep(1);
                    String info = "This is " + (i++) + "-th info from client";
                    buffer.clear();
                    buffer.put(info.getBytes());

                    while (buffer.hasRemaining()) {
                        System.out.println(buffer);
                        socketChannel.write(buffer);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (socketChannel != null) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }
}
