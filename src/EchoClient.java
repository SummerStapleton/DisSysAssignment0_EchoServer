import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        try (
                // establish a socket with the server
                Socket echoSocket = new Socket(hostName, portNumber);
                // create a writer to print output
                PrintWriter socketOut = new PrintWriter(echoSocket.getOutputStream(), true);
                // create a reader to take information from the server
                BufferedReader socketIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                // create a reader to take information typed into the console
                BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))
                ) {
            System.out.println("Connection success");
            String consoleInput;
            while ((consoleInput = consoleIn.readLine()) != null) {
                socketOut.println(consoleInput);
                System.out.println("echo: " + socketIn.readLine());
            }


        } catch (UnknownHostException e) {
            System.err.println("Can't connect to host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O error with host: " +
                    hostName);
            System.exit(1);
        }
    }
}

/*
public class ExampleEchoClient {
    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java ExampleEchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
*/