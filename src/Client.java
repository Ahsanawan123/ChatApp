import java.io.*;
import java.net.*;

public class Client {
    private String serverAddress;
    private int port;
    private String userId;

    // Constructor with server address and port
    public Client(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    // Constructor with server address, port, and user ID
    public Client(String serverAddress, int port, String userId) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.userId = userId;
    }

    // Start the client
    public void start() {
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server");

            // Send the initial username to the server (if any)
            output.println(userId != null ? userId : "");

            // Receive the assigned username from the server
            this.userId = serverInput.readLine();
            System.out.println("Assigned username: " + this.userId);

            System.out.println("Welcome to the Chat room! Type away to chat with your friends!\n");

            // Thread to read messages from the server
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

            // Read user input and send to server
            String userInput;
            while ((userInput = input.readLine()) != null) {
                output.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Check if correct number of arguments are provided
        if (args.length < 2) {
            System.out.println("Usage: java Client <server_ip> <server_port> [<user_id>]");
            return;
        }

        // Parse arguments
        String serverAddress = args[0];
        int port = Integer.parseInt(args[1]);
        String userId = args.length == 3 ? args[2] : null;

        // Create client instance and start it
        Client client = userId == null ? new Client(serverAddress, port) : new Client(serverAddress, port, userId);
        client.start();
    }
}
