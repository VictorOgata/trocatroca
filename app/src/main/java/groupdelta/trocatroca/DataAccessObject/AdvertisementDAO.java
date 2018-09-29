package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;

import groupdelta.trocatroca.Entities.Advertisement;

import static android.widget.Toast.LENGTH_LONG;

public class AdvertisementDAO extends DbConection {

    private String AdID;

    public AdvertisementDAO() {
        super();
        AdID = getFirebaseReference().child("Anuncios").push().getKey();
    }

    public void saveNewAd(Context context, Advertisement ad){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child("Anuncios").child(this.AdID).setValue(ad);
            getFirebaseReference().child("Itens").child("@"+ad.getItem()).setValue(ad.getItem());
            for(Map.Entry<String,String> map : ad.getWishList().entrySet()){
                getFirebaseReference().child("Itens").child("@"+map.getValue()).setValue(ad.getItem());}
        }
        else{
            Toast.makeText(context, (String) "User nao autenticado.", LENGTH_LONG).show();
        }
    }
}
