package groupdelta.trocatroca.Entities;

public class Message {
    private String textMessage;
    private String messageTime;

    public Message() {
    }

    public String getTextMessage() {
        return textMessage;
    }
    public String getTime() {
        return messageTime;
    }

    public void setTime(String messageTime) {
        this.messageTime = messageTime;
    }
    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
