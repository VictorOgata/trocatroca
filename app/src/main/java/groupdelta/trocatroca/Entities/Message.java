package groupdelta.trocatroca.Entities;

import android.text.Editable;

public class Message {
    private String textMessage,textMessage1;
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
    public String getUser2Message() {
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
    public void setUser1Message(String textMessage) {
        this.textMessage = textMessage;
    }
    public void setUser2Message(String textMessage1) {
        this.textMessage1 = textMessage1;
    }
    public void setUserID1(String userID1) {
        this.userID1 = userID1;
    }
    public void setUserID2(String userID2){this.userID2 = userID2;}

}
