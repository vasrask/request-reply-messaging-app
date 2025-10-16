import java.util.ArrayList;

public class Account {
    private String Username;
    private int AuthToken;
    private ArrayList<Message> MessageBox;
    private int messageIdCounter = 0;

    public Account(String username, int authToken, ArrayList<Message> messageBox){
        this.Username = username;
        this.AuthToken = authToken;
        this.MessageBox = messageBox;
    }

    public void setUsername(String username){
        Username = username;
    }
    public void setAuthToken(int authToken){
        AuthToken = authToken;
    }
    public void setMessageBox(ArrayList<Message> messageBox){
        MessageBox = messageBox;
    }
    public void setMessageIdCounter(int messageIdCounter) {
        this.messageIdCounter = messageIdCounter;
    }

    public String getUsername(){
        return Username;
    }
    public int getAuthToken(){
        return AuthToken;
    }
    public ArrayList<Message> getMessageBox(){
        return MessageBox;
    }

    public int getMessageIdCounter() {
        return messageIdCounter;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Username.equals(that.Username) &&
                AuthToken == that.AuthToken &&
                MessageBox.equals(that.MessageBox) &&
                messageIdCounter == that.messageIdCounter;
    }
}

