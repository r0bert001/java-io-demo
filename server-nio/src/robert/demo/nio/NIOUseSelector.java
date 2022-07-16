package robert.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOUseSelector {
    private static final int port = 9002;
    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if(selectionKey.isReadable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer dst = ByteBuffer.allocate(1024);
                        int len = socketChannel.read(dst);
                        System.out.println(new String(dst.array(),0,len));
                    }else if(selectionKey.isAcceptable()){
                        SocketChannel socketChannel = channel.accept();
                        if(socketChannel!=null){
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
