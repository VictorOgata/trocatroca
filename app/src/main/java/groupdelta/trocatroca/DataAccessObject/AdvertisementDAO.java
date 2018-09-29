package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import java.util.Map;

import groupdelta.trocatroca.Entities.Anuncio;

import static android.widget.Toast.LENGTH_LONG;

public class AdvertisementDAO extends Conexao {

    private String AdID;

    public AdvertisementDAO() {
        super();
        AdID = getFirebaseReference().child("Anuncios").push().getKey();
    }

    public void saveNewAd(Context context, Anuncio ad){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child("Anuncios").child(this.AdID).setValue(ad);
            getFirebaseReference().child("Itens").child(ad.getItem()).child(this.AdID).setValue("Oferecendo%"+this.AdID);
            for(Map.Entry<String,String> map : ad.getWishList().entrySet()){
                getFirebaseReference().child("Itens").child(map.getValue()).child(this.AdID).setValue("Buscando%"+this.AdID);}
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }
}
