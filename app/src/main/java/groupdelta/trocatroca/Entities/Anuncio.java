package groupdelta.trocatroca.Entities;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

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

    public Anuncio() {
    }

    public void saveNewAd(Context context){
        DatabaseReference referenciaFirebase = Conexao.getFirebaseReference();
        FirebaseUser firebaseUser = Conexao.getFirebaseAuth().getCurrentUser();
        if (firebaseUser != null) {
            referenciaFirebase.child("Anuncios").push().setValue(this);
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
        this.item = item;
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
    public void setWishList(Map<String, String> wishList) {
        WishList = wishList;
    }


    public String getHost() { return host; }
}
