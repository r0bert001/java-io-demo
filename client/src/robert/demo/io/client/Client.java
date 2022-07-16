package robert.demo.io.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int port = 9002;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input client number: ");
        int no = scanner.nextInt();
        System.out.println("Client number: "+no);
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1",port);
            OutputStream out = socket.getOutputStream();
            while (true){
                System.out.println("Client "+no+" input message: ");
                String s = scanner.next();
                if(s.trim().equals("quit")){
                    break;
                }
                out.write((no+" : "+s).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
