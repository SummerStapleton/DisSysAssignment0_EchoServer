import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Summer on 1/28/2018.
 */
public class EchoThread implements Runnable {

    private Socket clientSocket;
    InputStreamReader fromClient = null;
    OutputStreamWriter toClient = null;

    EchoThread(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {

        try {
            fromClient = new InputStreamReader(clientSocket.getInputStream());
            toClient = new OutputStreamWriter(clientSocket.getOutputStream());
            Termination term = new Termination();
            boolean  stayAlive = true;

            while(stayAlive) {
                int returned = fromClient.read();
                if (returned == -1) {
                    break;
                }
                char input = (char)returned;
                if (Character.isLetter(input)) {
                    toClient.write(input);
                    if (term.terminate(input)) {
                        close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to read or write input.");
        }
        finally {
            close();
        }
    }

    public void close() {
        try {
            fromClient.close();
            toClient.close();
            clientSocket.close();
        } catch(IOException e) {
            System.out.println("Connection closed.");
        }
    }
}

class Termination {
    int state;

    Termination(){
        state = 0;
    }

    boolean terminate(char input) {
        if (input == 'q') {
            state = 1;
        }
        if (input == 'u' && state == 1) {
            state = 2;
        }
        if (input == 'i' && state == 2) {
            state = 3;
        }
        if (input == 't' && state == 3) {
            state = 4;
        }
        else {
            state = 0;
        }

        return state == 4;
    }
}
