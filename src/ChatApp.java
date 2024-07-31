public class ChatApp {
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("server")) {
                Server server = new Server(12345);
                server.start();
            } else if (args[0].equalsIgnoreCase("client")) {
                if (args.length == 4) {
                    String serverAddress = args[1];
                    int port = Integer.parseInt(args[2]);
                    String userId = args[3];
                    Client client = new Client(serverAddress, port, userId);
                    client.start();
                } else if (args.length == 3) {
                    String serverAddress = args[1];
                    int port = Integer.parseInt(args[2]);
                    Client client = new Client(serverAddress, port);
                    client.start();
                } else {
                    System.out.println("Usage: java ChatApp client <server-ip> <port> [<user-id>]");
                }
            } else {
                System.out.println("Usage: java ChatApp <server|client>");
            }
        } else {
            System.out.println("Usage: java ChatApp <server|client>");
        }
    }
}
