package groupdelta.trocatroca.Entities;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.Conexao;

import static android.widget.Toast.LENGTH_LONG;

public class Anuncio {

    //Advertisement itemname
    private String item;
    //Advertisement description
    private String description;
    //User contact info
    private Map<String,String> WishList;
    //User Advertisement state (Country area)
    private String state;
    //User Advertisement city
    private String city;
    //User Advertisement ID
    private String host;
    //Advertisement type(game or book)
    private String type;
    private String AdID;
    public Anuncio() {
        DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        AdID = referenciaFirebase.child("Anuncios").push().getKey();
    }

    public void saveNewAd(Context context){
        DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            referenciaFirebase.child("Anuncios").child(this.AdID).setValue(this);
            referenciaFirebase.child("Itens").child(this.item).child(this.AdID).setValue("Oferecendo%"+this.AdID);

        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
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
    public void setItem(String item) {
        this.item = item.replace(" ", "_").toUpperCase();
    }
    public Map<String, String> getWishList() {
        return WishList;
    }


    public String getType() { return type; }
    public void setType(String type) {
        this.type = type;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public void setWishList(String []wList) {
        HashMap<String,String> wishList = new HashMap<String, String>();
        DatabaseReference referenciaFirebase;
        FirebaseUser firebaseUser;
        referenciaFirebase = Conexao.getFirebaseReference();
        firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        for(int i=0;i<wList.length;i++){
            wishList.put("@"+wList[i], wList[i]);
            referenciaFirebase.child("Itens").child(wList[i]).child(this.AdID).setValue("Buscando%"+this.AdID);}

        this.WishList = wishList;
    }


    public String getHost() { return host; }
}
