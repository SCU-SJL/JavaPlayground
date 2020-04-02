package io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ShaoJiale
 * Date: 2020/4/2
 */
public class Server {
    private static final int READ_SIZE = 1024;
    private static final int WRITE_SIZE = 128;

    public static void main(String[] args) throws IOException {
        // open a channel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8000));

        // open a selector
        Selector selector = Selector.open();

        // set non-blocking and register OP_ACCEPT event
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer readBuf = ByteBuffer.allocate(READ_SIZE);
        ByteBuffer writeBuf = ByteBuffer.allocate(WRITE_SIZE);

        writeBuf.put("received".getBytes());
        writeBuf.flip();


        while (true) {
            int nReady = selector.select();
            if (nReady == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> it = selectionKeys.iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                if (key.isValid()) {
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        readBuf.clear();
                        int readN = socketChannel.read(readBuf);
                        readBuf.flip();

                        if (readN != -1) {
                            System.out.println("received: " + new String(readBuf.array()).trim());
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else {
                            socketChannel.close();
                        }
                    } else if (key.isWritable()) {
                        writeBuf.rewind();
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.write(writeBuf);
                        key.interestOps(SelectionKey.OP_READ);
                    }
                }
            }
        }
    }
}
