import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessagingClient {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public MessagingClient(String serverIp, int serverPort) {
        try {
            this.socket = new Socket(serverIp, serverPort);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runFunction(int functionId, String[] functionArgs) {
        try {
            out.print(functionId + " ");

            for (String arg : functionArgs) {
                out.print(arg + " ");
            }
            out.println();
            String response = in.readLine();
            processResponse(response, functionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processResponse(String response, int functionId) {
        switch (functionId) {
            case 1:
                handleCreateAccountResponse(Integer.parseInt(response));
                break;
            case 2:
                handleShowAccountsResponse(response);
                break;
            case 3:
                handleSendMessageResponse(response);
                break;
            case 4:
                handleShowInboxResponse(response);
                break;
            case 5:
                handleReadMessageResponse(response);
                break;
            case 6:
                handleDeleteMessageResponse(response);
                break;
            default:
                System.out.println("Unknown functionId: " + functionId);
        }
    }

    private void handleCreateAccountResponse(int response) {
        switch (response) {
            case -1:
                System.out.println("Sorry, the user already exists");
                break;
            case -2:
                System.out.println("Invalid Username");
                break;
            default:
                System.out.println(response);
        }
    }

    private void handleShowAccountsResponse(String response) {
        if (response == null) {
            System.out.println("There are no accounts");
        }
        else{
            System.out.println(response.replace('/','\n'));
        }
    }

    private void handleSendMessageResponse(String response) {
        switch (response) {
            case "OK":
                System.out.println("OK");
                break;
            case "User does not exist":
                System.out.println("User does not exist");
                break;
        }
    }

    private void handleShowInboxResponse(String response) {
        if (response == null) {
            System.out.println("There are no messages");
        }
        else{
            System.out.println(response.replace('/', '\n'));
        }
    }

    private void handleReadMessageResponse(String response) {
        switch (response) {
            case "Message ID does not exist":
                System.out.println("Message ID does not exist");
                break;
            default:
                System.out.println(response);
        }
    }

    private void handleDeleteMessageResponse(String response) {
        switch (response) {
            case "OK":
                System.out.println("OK");
                break;
            case "Message does not exist":
                System.out.println("Message does not exist");
                break;
        }
    }

    public void close() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args,String serverIp, int serverPort) {
        MessagingClient client = new MessagingClient(serverIp, serverPort);
    }
}


