package groupdelta.trocatroca.Entities;

public class Message {
    private String textMessage;
    private long messageTime;
    private String Usermessage;
    private String userID1,userID2;

    public Message() {
    }
    public String getUserID1() {
        return userID1;
    }
    public String getUserID2() {
        return userID2;
    }
    public String getUser1Message() {
        return Usermessage;
    }
    public String getTextMessage() {
        return textMessage;
    }
    public long getTime() {
        return messageTime;
    }
    public void setT(long messageTime) {
        this.messageTime = messageTime;
    }
    public void setTime(long messageTime) {
        this.messageTime = messageTime;
    }
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
