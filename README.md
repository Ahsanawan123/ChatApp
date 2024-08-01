# ChatApp
This is a simple chat application written in Java. It allows multiple clients to connect to a server and communicate with each other. The server broadcasts messages from any client to all other connected clients.

Prerequisites
- Java Development Kit (JDK) installed on your machine.
- A terminal or command prompt to run commands.

Directory Structure
ChatApp/
│
├── src/
│   ├── ChatApp.java
│   ├── Client.java
│   └── Server.java
│
├── classes/
│   (compiled classes will be placed here)
│
└── Makefile

Compilation and Execution
- Compiling the Code
    Before running the server or client, you need to compile the Java source files. Open your terminal, navigate to the ChatApp directory, and run:
     make compile
    This will compile all Java source files and place the compiled classes in the classes directory.

Running the Server
- To start the server, use the following command:
    make run-server
The server will start on port 12345 and will display a message indicating that it is running. You can broadcast a message to all connected clients by typing your message and pressing Enter.

Running the Client
- To run the client with the your IP address and port , use:
    java -cp classes ChatApp client [IP] [Port#]
- Or if you want to run easily acess go into make file and edit the following: 
    # Rule to run the client with the default IP address and port
run-client:
	java -cp classes ChatApp client 127.0.0.1 12345 <--here adjust your ip and port, this is a virtual machine I used so this won't work
- and the run the following command:
    make run-client
- This will connect the client to the server. The client will be assigned a username by the server, and you can start typing messages to send them to the chat room.

Running the Client with a Custom Username
 - If you want to run the client with a custom username, use the following command:
    make run-client-custom username=<your-username>
- Replace <your-username> with your desired username. This will connect the client to the server with the specified username.

Cleaning Up
- To remove all compiled classes and clean up the directory, use:
    make clean

Usage

    Starting the Server:
        Open a terminal and navigate to the ChatApp directory.
        Run make compile to compile the code.
        Run make run-server to start the server.
        The server will display a message indicating it has started and is listening on port 12345.

    Connecting Clients:
        Open a new terminal for each client.
        Navigate to the ChatApp directory.
        Run make run-client to start a client with a default username.
        Alternatively, run make run-client-custom username=<your-username> to start a client with a custom username.
        The client will connect to the server, and you can start sending messages.

    Sending Messages:
        Once connected, type your message and press Enter to send it to the chat room.
        All connected clients will receive the message.

    Broadcasting from Server:
        On the server terminal, you can type a message and press Enter to broadcast it to all connected clients.

    Cleaning Up:
        Run make clean to remove all compiled classes.

Notes

    Ensure that the port 12345 is open and not being used by another application on your machine.
    You can modify the port number in the Server class constructor if needed, but make sure to use the same port when starting the client.

Troubleshooting

    If you encounter any issues, ensure that your Java environment is correctly set up and the JDK is installed.
    Verify that there are no network issues preventing the client from connecting to the server.
    Check for any typos in the commands.

By following this guide, you should be able to set up and run the chat application on your machine. Enjoy chatting!