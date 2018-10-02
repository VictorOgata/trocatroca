package groupdelta.trocatroca.Entities;

import java.util.HashMap;
import java.util.Map;

public class Advertisement {

    //Advertisement itemname
    private String item;
    //Advertisement description
    private String description;
    private Map<String,String> wishList;
    //User Advertisement state (Country area)
    private String state;
    //User Advertisement city
    private String city;
    //User Advertisement ID
    private String host;
    //Advertisement type(game or book)
    private String type;

    public Advertisement() {
    }

    public String getItem() {
        return item;
    }
    public String getDescription() {
        return description;
    }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public String getType() { return type; }
    public String getHost() { return host; }
    public Map<String, String> getWishList() {
        return wishList;
    }

    public void setItem(String item) {
        this.item = item.replace(" ", "_").toUpperCase();
    }
    public void setWishList(Map<String, String>  wishList) {
        this.wishList = wishList;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setHost(String host) {
        this.host = host;
    }

    public Map<String,String> makeWishList(String [] wList){
        HashMap<String,String> wishes= new HashMap<String,String>();
        for(String wish : wList){
            wishes.put("@"+wish,wish);
        }
        return wishes;
    }

    public void shapeHashMapIntoAdvertisement(HashMap AdvertHashMap){
        if(AdvertHashMap!=null) {
            this.setItem(AdvertHashMap.containsKey("item") ? AdvertHashMap.get("item").toString() : "NA");
            this.setDescription(AdvertHashMap.containsKey("description") ? AdvertHashMap.get("description").toString() : "NA");
            this.setWishList(AdvertHashMap.containsKey("wishList") ? (Map<String, String>) AdvertHashMap.get("wishList") : new HashMap<String, String>());
            this.setCity(AdvertHashMap.containsKey("city") ? AdvertHashMap.get("city").toString() : "NA");
            this.setState(AdvertHashMap.containsKey("state") ? AdvertHashMap.get("state").toString() : "NA");
            this.setHost(AdvertHashMap.containsKey("host") ? AdvertHashMap.get("host").toString() : "NA");
            this.setType(AdvertHashMap.containsKey("type") ? AdvertHashMap.get("type").toString() : "NA");
        }
    }
}
