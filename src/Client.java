import java.io.*;
import java.net.*;

public class Client {
    private String serverAddress;
    private int port;
    private String userId;
    private static int userCount = 1;
    private static final Object lock = new Object();

    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
        synchronized (lock) {
            this.userId = "user" + userCount++;
            //System.out.println("Assigned username: " + this.userId); // Debugging
        }
    }

    public Client(String serverAddress, int port, String userId) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.userId = userId;
    }

    public void start() {
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server");

            Thread readThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = serverInput.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            readThread.start();

            String userInput;
            while ((userInput = input.readLine()) != null) {
                output.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Client <server_ip> <server_port> [<user_id>]");
            return;
        }

        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);
        String userId = args.length == 3 ? args[2] : null;

        Client client = userId == null ? new Client(serverAddress, port) : new Client(serverAddress, port, userId);
        client.start();
    }
}
