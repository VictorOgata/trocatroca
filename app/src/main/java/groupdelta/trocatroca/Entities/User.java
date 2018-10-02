package groupdelta.trocatroca.Entities;

import java.util.HashMap;

public class User {

    //User nickname
    private String nick;
    //User email
    private String email;
    //User contact info
    private String CInfo;
    //User state (Country area)
    private String state;
    //User city
    private String city;
    // User avaliation points
    private String avPoints;


    public User() {
    }
    public String getNick() {
        return nick;
    }
    public String getEmail() {
        return email;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getCInfo() { return CInfo; }
    public String getAvPoints() {
        return avPoints;
    }


    public void setNick(String nick) {
        this.nick = nick;
    }
    public void setCInfo(String CInfo) {
        this.CInfo = CInfo;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setAvPoints(String avPoints) {
        this.avPoints = avPoints;
    }

    public HashMap<String,String> returnUserHashMap(){
        HashMap<String,String> UserHashMap = new HashMap<>();
        UserHashMap.put("nick",(this.nick.isEmpty())? "":this.getNick());
        UserHashMap.put("email",(this.email.isEmpty())? "":this.getEmail());
        UserHashMap.put("CInfo",(this.CInfo.isEmpty())? "":this.getCInfo());
        UserHashMap.put("state",(this.state.isEmpty())? "":this.getState());
        UserHashMap.put("city",(this.city.isEmpty())? "":this.getCity());
        return UserHashMap;
    }
    public void shapeHashMapIntoUser(HashMap UserHashMap){
        this.setNick(UserHashMap.containsKey("nick")? UserHashMap.get("nick").toString():"NA");
        this.setEmail(UserHashMap.containsKey("email")? UserHashMap.get("email").toString():"NA");
        this.setCInfo(UserHashMap.containsKey("CInfo")? UserHashMap.get("CInfo").toString():"NA");
        this.setState(UserHashMap.containsKey("state")? UserHashMap.get("state").toString():"NA");
        this.setCity(UserHashMap.containsKey("city")? UserHashMap.get("city").toString():"NA");
    }
}
