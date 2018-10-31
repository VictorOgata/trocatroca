package groupdelta.trocatroca.Entities;

import java.util.HashMap;

public class TradeRequest {
    private String host;
    private String target;
    private String adID;
    private String message;
    private String requestText;

    public TradeRequest() {
    }

    public String getHost() {
        return host;
    }
    public String getTarget() {
        return target;
    }
    public String getAdID() {
        return adID;
    }
    public String getMessage() {
        return message;
    }
    public String getRequestText() {
        return requestText;
    }

    public void setRequestText(String requestText) {
        this.requestText = requestText;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public void setAdID(String adID) {
        this.adID = adID;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String,String> returnHashMap(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("host",(this.host.isEmpty())? "":this.host);
        hashMap.put("target",(this.target.isEmpty())? "":this.target);
        hashMap.put("adID",(this.adID.isEmpty())? "":this.adID);
        hashMap.put("message",(this.message.isEmpty())? "":this.message);
        return hashMap;
    }
    public void shapeAs_HashMap(HashMap hashMap){
        this.setHost(hashMap.containsKey("host")? hashMap.get("host").toString():"NA");
        this.setTarget(hashMap.containsKey("target")? hashMap.get("target").toString():"NA");
        this.setAdID(hashMap.containsKey("adID")? hashMap.get("adID").toString():"NA");
        this.setMessage(hashMap.containsKey("message")? hashMap.get("message").toString():"NA");
    }
}
