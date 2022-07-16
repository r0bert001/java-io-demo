package robert.demo.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AIO {
    private static final int port = 9002;
    public static void main(String[] args) {
        try {
            AsynchronousServerSocketChannel asynchronousServerSocketChannel =  AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress("127.0.0.1",port));
            asynchronousServerSocketChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                @Override
                public void completed(AsynchronousSocketChannel result, Object attachment) {
                    asynchronousServerSocketChannel.accept(null,this);
                    while (result.isOpen()){
                        ByteBuffer dst = ByteBuffer.allocate(1024);
                        Future<Integer> read = result.read(dst);
                        try {
                            Integer len = read.get();
                            System.out.println(new String(dst.array(),0,len, StandardCharsets.UTF_8));
                            dst.clear();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Throwable exc, Object attachment) {

                }
            });
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
