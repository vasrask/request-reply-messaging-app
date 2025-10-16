import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class MessagingServer {
    private ArrayList<Account> accounts = new ArrayList<>();

    private ArrayList<Integer> authTokens = new ArrayList<>();

    public MessagingServer(int port) {
        this.accounts = new ArrayList<>();
    }

    public void start(int portNumber) {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Server is listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                ServerThread serverThread = new ServerThread(clientSocket, this);
                serverThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized int createAccount(String username) {
        int authToken = generateAuthToken();
        Account account = new Account(username,authToken,new ArrayList<>());
        for (Account acc : accounts) {
            if (acc.getUsername().equals(username)) {
                return -1;
            }
        }
        if (!username.matches("^[a-zA-Z]+$")){
            return -2;
        }

        accounts.add(account);

        return authToken;
    }

    private int generateAuthToken() {
        List<Integer> digits = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            digits.add(i);
        }
        String randomNumber = "";
        boolean exists = true;
        while(exists){

            Collections.shuffle(digits);

            for(int i = 0; i < 4; i++){
                randomNumber += digits.get(i).toString();
            }
            if (!(authTokens.contains(randomNumber) || (randomNumber.charAt(0) == '0'))){
                exists = false;
            }
        }
        return Integer.parseInt(randomNumber);
    }

    public synchronized String showAccounts(int authToken) {
        Account acc = checkIfAccountExists(authToken);
        if (acc == null){
            return "Invalid Auth Token";
        }

        ArrayList<String> accountsList = new ArrayList<>();
        int i = 1;
        for (Account account : accounts) {
            accountsList.add(i++ + ". " + account.getUsername());
        }
        String response = String.join("/",accountsList);
        return response;
    }

    public synchronized String sendMessage(int authToken, String recipient, String messageBody) {
        Account acc = checkIfAccountExists(authToken);
        if (acc == null){
            return "Invalid Auth Token";
        }

        Account rec = null;
        boolean recipientExists = false;
        for (Account account : accounts) {
            if (account.getUsername().equals(recipient)) {
                recipientExists = true;
                rec = account;
                break;
            }
        }
        if (!recipientExists){
            return "User does not exist";
        }
        int id = rec.getMessageIdCounter() + 1;
        rec.setMessageIdCounter(id);

        Message message = new Message(false,acc.getUsername(),rec.getUsername(),messageBody,id);

        rec.getMessageBox().add(message);

        return "OK";
    }

    public synchronized String showInbox(int authToken) {
        Account acc = checkIfAccountExists(authToken);
        if (acc == null){
            return "Invalid Auth Token";
        }

        ArrayList<String> messagesList = new ArrayList<>();
        for (Message message : acc.getMessageBox()) {
            String suffix = "";
            if (message.getIsRead() == false){
                suffix = "*";
            }
            messagesList.add(message.getId() + ". from: " + message.getSender() + suffix);
        }
        String response = String.join("/",messagesList);
        return response;
    }

    public synchronized String readMessage(int authToken, int messageId) {
        Account acc = checkIfAccountExists(authToken);
        if (acc == null){
            return "Invalid Auth Token";
        }

        List<Message> messageBox = acc.getMessageBox();

        Message mess = null;
        for (Message message : messageBox) {
            if (message.getId() == messageId) {
                if (!message.getIsRead()) {
                    message.setIsRead(true);
                    mess = message;
                    break;
                }
            }
        }
        if (mess == null){
            return "Message ID does not exist";
        }
        else{
            return "(" + mess.getSender() + ") " + mess.getBody();
        }
    }

    public synchronized String deleteMessage(int authToken, int messageId) {
        Account acc = checkIfAccountExists(authToken);
        if (acc == null){
            return "Invalid Auth Token";
        }

        List<Message> messageBox = acc.getMessageBox();

        for (Iterator<Message> iterator = messageBox.iterator(); iterator.hasNext();) {
            Message message = iterator.next();
            if (message.getId() == messageId) {
                iterator.remove();
                return "OK";
            }
        }

        return "Message does not exist";
    }

    public Account checkIfAccountExists(int authToken){
        Account acc = null;
        for (Account account : accounts) {
            if (account.getAuthToken() == authToken) {
                acc = account;
                break;
            }
        }
            return acc;
    }
    public static void main(String[] args, int portNumber) {
        MessagingServer server = new MessagingServer(portNumber);
        server.start(portNumber);
    }
}
