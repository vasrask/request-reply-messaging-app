import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    private final Socket clientSocket;
    private final MessagingServer server;

    public ServerThread(Socket clientSocket, MessagingServer server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String request = reader.readLine();
            String[] parts = request.split(" ");
            String response = null;
            int functionId = Integer.parseInt(parts[0]);
            String username = "";
            int authToken = 0;
            ArrayList<String> messageArgs = new ArrayList<>();
            String messageArg = "";
            if (parts.length > 3){
                for (int i = 3; i < parts.length; i++) {
                    messageArgs.add(parts[i]);
                }
                messageArg = String.join(" ",messageArgs);
            }
            if (functionId == 1){
                username = parts[1];
            }
            else{
                authToken = Integer.parseInt(parts[1]);
            }

            switch (functionId) {
                case 1:
                    response = Integer.toString(handleCreateAccount(username));
                    writer.write(response);
                    break;
                case 2:
                    response = handleShowAccounts(authToken);
                    writer.write(response);
                    break;
                case 3:
                    response = handleSendMessage(authToken, parts[2], messageArg);
                    writer.write(response);
                    break;
                case 4:
                    response = handleShowInbox(authToken);
                    writer.write(response);
                    break;
                case 5:
                    response = handleReadMessage(authToken, Integer.parseInt(parts[2]));
                    writer.write(response);
                    break;
                case 6:
                    response = handleDeleteMessage(authToken, Integer.parseInt(parts[2]));
                    writer.write(response);
                    break;
                default:
                    writer.println("Invalid function ID");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int handleCreateAccount(String username) {
        return server.createAccount(username);
    }

    private String handleShowAccounts(int authToken) {
        return server.showAccounts(authToken);
    }

    private String handleSendMessage(int authToken, String recipient, String messageBody) {
        return server.sendMessage(authToken, recipient, messageBody);
    }

    private String handleShowInbox(int authToken) {
        return server.showInbox(authToken);
    }

    private String handleReadMessage(int authToken, int messageId) {
        return server.readMessage(authToken, messageId);
    }

    private String handleDeleteMessage(int authToken, int messageId) {
        return server.deleteMessage(authToken, messageId);
    }
}
