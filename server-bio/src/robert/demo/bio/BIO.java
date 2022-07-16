package robert.demo.bio;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BIO {

    public static int port = 9001;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                int len ;
                byte[] b = new byte[1024];
                while ((len=in.read(b))!=-1){
                    System.out.println(new String(b,0,len));
                }
                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
