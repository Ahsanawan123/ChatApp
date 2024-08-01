# Target to compile all Java source files and place the output in the 'classes' directory
all: compile

# Rule to compile Java source files
compile:
	javac -d classes src/*.java

# Rule to run the server
run-server:
	java -cp classes ChatApp server

# Rule to run the client with the default IP address and port
run-client:
	java -cp classes ChatApp client 127.0.0.1 12345

# Rule to run the client with a custom username
run-client-custom:
	java -cp classes ChatApp client 127.0.0.1 12345 $(username)

# Rule to clean up the compiled classes directory
clean:
	rm -rf classes/*
