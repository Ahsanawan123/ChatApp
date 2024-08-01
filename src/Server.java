import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private int port;
    private Set<PrintWriter> clientWriters = new HashSet<>();
    private AtomicInteger userCount = new AtomicInteger(1);

    public Server(int port) {
        this.port = port;
    }

    // Start the server
    public void start() {
        System.out.println("Server started on port " + port);
        System.out.println("Hello admin, welcome to the server! If you want to broadcast a message to all the current clients, please type your message and press enter.\n");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // Thread to read server console input
            new Thread(this::readServerInput).start();

            // Accept client connections
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read server console input and broadcast to clients
    private void readServerInput() {
        try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            String message;
            while ((message = consoleInput.readLine()) != null) {
                sendToAllClients("Server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send a message to all connected clients
    public void sendToAllClients(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter output;
        private String username;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                output = new PrintWriter(socket.getOutputStream(), true);
                clientWriters.add(output);

                // Receive initial username from client
                String initialUsername = input.readLine();
                if (initialUsername == null || initialUsername.isEmpty()) {
                    this.username = "user" + userCount.getAndIncrement();
                } else {
                    this.username = initialUsername;
                }

                // Send the assigned username back to the client
                output.println(this.username);

                // Notify all clients about the new client
                sendToAllClients(username + " has joined the chat");

                // Read and broadcast messages from the client
                String message;
                while ((message = input.readLine()) != null) {
                    sendToAllClients(username + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (output != null) {
                    clientWriters.remove(output);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Notify all clients about the client leaving
                sendToAllClients(username + " has left the chat");
            }
        }
    }

    public static void main(String[] args) {
        // Check if correct number of arguments are provided
        if (args.length != 1) {
            System.out.println("Usage: java Server <port>");
            return;
        }

        // Parse port argument and start server
        int port = Integer.parseInt(args[0]);
        new Server(port).start();
    }
}
