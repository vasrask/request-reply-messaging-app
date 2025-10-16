import java.io.Serializable;

public class Message implements Serializable {
    private boolean IsRead;
    private String Sender;
    private String Receiver;
    private String Body;
    private int Id;
    public Message(boolean isRead, String sender,String receiver,String body, int id){
        this.IsRead = isRead;
        this.Sender = sender;
        this.Receiver = receiver;
        this.Body = body;
        this.Id = id;
    }

    public void setIsRead(boolean isRead){
        IsRead = isRead;
    }
    public void setSender(String sender){
        Sender = sender;
    }
    public void setReceiver(String receiver){
        Receiver = receiver;
    }
    public void setId(int id){
        Id = id;
    }
    public void setBody(String body){
        Body = body;
    }

    public boolean getIsRead(){
        return IsRead;
    }
    public String getSender(){
        return Sender;
    }
    public String getReceiver(){
        return Receiver;
    }
    public String getBody(){
        return Body;
    }
    public int getId(){
        return Id;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message that = (Message) o;
        return IsRead == that.IsRead &&
                Sender.equals(that.Sender) &&
                Receiver.equals(that.Receiver) &&
                Body.equals(that.Body) &&
                Id == that.Id;
    }

}
