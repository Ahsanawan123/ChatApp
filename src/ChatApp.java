public class ChatApp {
    public static void main(String[] args) {
        // Check if arguments are provided
        if (args.length > 0) {
            // If first argument is "server", start the server
            if (args[0].equalsIgnoreCase("server")) {
                Server server = new Server(12345);
                server.start();
            } 
            // If first argument is "client", start the client
            else if (args[0].equalsIgnoreCase("client")) {
                // If three additional arguments are provided, use them as server address, port, and user ID
                if (args.length == 4) {
                    String serverAddress = args[1];
                    int port = Integer.parseInt(args[2]);
                    String userId = args[3];
                    Client client = new Client(serverAddress, port, userId);
                    client.start();
                } 
                // If two additional arguments are provided, use them as server address and port
                else if (args.length == 3) {
                    String serverAddress = args[1];
                    int port = Integer.parseInt(args[2]);
                    Client client = new Client(serverAddress, port);
                    client.start();
                } 
                // If incorrect arguments are provided, display usage instructions
                else {
                    System.out.println("Usage: java ChatApp client <server-ip> <port> [<user-id>]");
                }
            } 
            // If first argument is neither "server" nor "client", display usage instructions
            else {
                System.out.println("Usage: java ChatApp <server|client>");
            }
        } 
        // If no arguments are provided, display usage instructions
        else {
            System.out.println("Usage: java ChatApp <server|client>");
        }
    }
}
