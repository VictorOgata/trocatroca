package groupdelta.trocatroca.Entities;

import java.util.HashMap;

public class Trade {
    //Host Trader
    private String hTrader;
    //Target Trader
    private String tTrader;
    //Advetisement ID that the trade is about
    private String adID;
    //Trade status: waiting,one_sided,complete
    private String status;

    public Trade() {
    }

    public String gethTrader() {
        return hTrader;
    }
    public String getAdID() {
        return adID;
    }
    public String gettTrader() {
        return tTrader;
    }
    public String getStatus() {
        return status;
    }

    public void sethTrader(String hTrader) {
        this.hTrader = hTrader;
    }
    public void settTrader(String rTrader) {
        this.tTrader = rTrader;
    }
    public void setAdID(String adID) {
        this.adID = adID;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String,String> returnHashMap(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("hTrader",(this.hTrader.isEmpty())? "":this.hTrader);
        hashMap.put("tTrader",(this.tTrader.isEmpty())? "":this.tTrader);
        hashMap.put("adID",(this.adID.isEmpty())? "":this.adID);
        hashMap.put("status",(this.status.isEmpty()? "":this.status));
        return hashMap;
    }

    public void shapeAs_HashMap(HashMap hashMap){
        this.sethTrader(hashMap.containsKey("hTrader")? hashMap.get("hTrader").toString():"NA");
        this.settTrader(hashMap.containsKey("tTrader")? hashMap.get("tTrader").toString():"NA");
        this.setAdID(hashMap.containsKey("adID")? hashMap.get("adID").toString():"NA");
        this.setStatus(hashMap.containsKey("status")? hashMap.get("status").toString():"NA");
    }
}
