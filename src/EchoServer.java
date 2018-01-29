import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by Summer on 1/28/2018.
 */
public class EchoServer{

    public static void main(String args[]) throws Exception{
        ServerSocket servSock;
        try {
            servSock = new ServerSocket(8080);

            while(true){
                EchoThread eThread;

                try{
                    eThread = new EchoThread(servSock.accept());
                    Thread thread = new Thread(eThread);
                    thread.start();
                } catch (IOException e) {
                    System.out.println("Accept failed on port 8080");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to listen on port 8080");
            System.exit(-1);
        }

    }
}
