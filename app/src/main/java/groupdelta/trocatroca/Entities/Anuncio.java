package groupdelta.trocatroca.Entities;

import java.util.HashMap;
import java.util.Map;

public class Anuncio {

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
    public Anuncio() {
        /*DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        AdID = referenciaFirebase.child("Anuncios").push().getKey();*/
    }

    /*public void saveNewAd(Context context){
        DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        String AdID = referenciaFirebase.child("Anuncios").push().getKey();
        if (firebaseUser != null) {
            referenciaFirebase.child("Anuncios").child(AdID).setValue(this);
            referenciaFirebase.child("Itens").child("@"+this.item).setValue(this.item);

        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }*/

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

    public void setItem(String item) {
        this.item = item.replace(" ", "_").toUpperCase();
    }
    public Map<String, String> getWishList() {
        return wishList;
    }
    public void setWishList(String []wList) {
       HashMap<String,String> wishList = new HashMap<String, String>();
        /*DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();*/
        for(String wish : wList)//int i=0;i<wList.length;i++){
            wishList.put("@"+wish,wish);
            //referenciaFirebase.child("Itens").child(wList[i]).child(this.AdID).setValue("Buscando%"+this.AdID);}
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
}
