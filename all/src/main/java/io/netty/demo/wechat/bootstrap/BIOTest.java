package io.netty.demo.wechat.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * todo 描述作用
 *
 * @author 逼哥
 * @date 2019/12/2
 */
public class BIOTest {
    private static final int PORT = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        new Thread(() -> {
            try {
                Socket socket2 = new Socket("localhost", PORT);
                OutputStream outputStream = socket2.getOutputStream();
                outputStream.write("Hello World".getBytes());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        Socket socket = serverSocket.accept();

        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) > 0) {
            System.out.println(new String(buffer, 0, len));
        }
    }
}
