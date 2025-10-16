# Request–Reply Messaging App (Java Sockets)

A simple client–server messaging application built in Java using **Sockets**, **I/O Streams**, and **Threads**.  
Each client communicates with the server via a request–reply protocol: the client sends a request, the server responds, and the connection is closed.
Multiple clients can create accounts, send messages to each other, and retrieve their inbox — all managed dynamically in memory by the server.

---

## Τechnologies Used

- Java SE 8+  
- TCP/IP Sockets  
- Multithreading  
- I/O Streams (ObjectInputStream / ObjectOutputStream)

---

##  Overview

| Class | Description |
|-------|--------------|
| **Message** | Represents a message with a unique message ID and sender/receiver info. |
| **Account** | Represents a user account. Includes a counter to assign unique message IDs. |
| **MessagingClient** | Manages client–side socket connection, sends requests, and reads server responses. |
| **MessagingServer** | Listens for incoming requests, manages all accounts and messages, and handles responses. |
| **ServerThread** | Handles requests from an individual client socket in a separate thread. |
| **Client** | Main entry point for running the client application. |
| **Server** | Main entry point for running the server application. |

---

## Building and Running the Application

###  2. Compile the Source Code
```
javac -d out src/*.java
```

### 2. Create Executable JAR Files

```
jar cfm Server.jar manifests/ServerManifest.txt -C out .
jar cfm Client.jar manifests/ClientManifest.txt -C out .
```
### 3. Run the Server
The server listens on the specified port:
```
java -jar Server.jar <port>
```
### 4. Run the Client
```
java -jar Client.jar <server_ip> <port> <FN_ID> <args>
```
> Note: Make sure each client session is run in its own terminal window.

## Supported Operations
|FN_ID |	Function |	Syntax |	Description|
|------| ------------|---------|---------------|
|1|	Create Account|	`java client <ip> <port> 1 <username>`|Creates a new account and returns a unique authentication token.|
|2|	Show Accounts|`java client <ip> <port> 2 <authToken>`|Displays a list of all registered users.|
|3|	Send Message|`java client <ip> <port> 3 <authToken> <recipient> <message_body>`|Sends a message to the specified recipient.|
|4|	Show Inbox|`java client <ip> <port> 4 <authToken>`	|Displays all messages for the user (unread messages are marked with *).|
|5|Read Message|`java client <ip> <port> 5 <authToken> <message_id>`|Displays the content of a message and marks it as read.|
|6|Delete Message|`java client <ip> <port> 6 <authToken> <message_id>`|Deletes the specified message.|

## Authentication Tokens
Each account receives a unique authToken upon creation. This token must be used in all subsequent client requests to:
-- Prevent users from reading other users’ data.
-- Prevent message spoofing.
The server maintains an in-memory mapping between tokens and user accounts.

## Notes
- All data (accounts, messages) exist only during runtime — no files are used.
- The server supports multiple concurrent clients via threads.
- Connections follow a request–reply pattern (one request per connection).

## Author
Vasiliki Raskopoulou

---
This project was developed as part of a Network Programming Assignment, focused on socket-based communication and multithreading in Java.