package groupdelta.trocatroca.Entities;

import android.text.Editable;

public class Message {
    private String textMessage1,chatid;
    private long messageTime;
    private String userID1,username;

    public Message() {
    }
    public String getUserID() {
        return userID1;
    }
    public String getUserMessage() {
        return textMessage1;
    }
    public String getChatid(){return chatid;}

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }
    public String getUsername(){
        return username;
    }

    public long getTime() {
        return messageTime;
    }
    public void setTime(long messageTime) {
        this.messageTime = messageTime;
    }
    public void setUserMessage(String textMessage1) {
        this.textMessage1 = textMessage1;
    }
    public void setUserID(String userID1) {
        this.userID1 = userID1;
    }
    public void setUsername(String username){
        this.username = username;
    }

}
