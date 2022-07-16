package robert.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class NIO {
    private static final int port = 9002;
    public static void main(String[] args) {
        List<SocketChannel> channelList = new ArrayList<SocketChannel>();
        try {
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.socket().bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            while (true){
                SocketChannel socketChannel = channel.accept();
                if(socketChannel!=null){
                    socketChannel.configureBlocking(false);
                    channelList.add(socketChannel);
                }
                for (int i = 0; i < channelList.size(); i++) {
                    if( channelList.get(i).isOpen()){
                        SocketChannel sChannel = channelList.get(i);
                        ByteBuffer dst = ByteBuffer.allocate(1024);
                        try{
                            int len = sChannel.read(dst);
                            if(len>0){
                                dst.flip();
                                System.out.println(new String(dst.array(),0,dst.limit()));
                            }
                        }catch (IOException E){
                            System.out.println("close a connection");
                            channelList.remove(i);
                        }

                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
