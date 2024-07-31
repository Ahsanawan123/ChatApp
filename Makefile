all: compile

compile:
	javac -d classes src/*.java

run-server:
	java -cp classes ChatApp server

run-client:
	java -cp classes ChatApp client 127.0.0.1 12345

clean:
	rm -rf classes/*
