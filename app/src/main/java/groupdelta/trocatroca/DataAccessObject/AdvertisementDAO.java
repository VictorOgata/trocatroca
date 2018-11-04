package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

import groupdelta.trocatroca.Entities.Advertisement;

import static android.widget.Toast.LENGTH_LONG;

public class AdvertisementDAO extends DbConection {

    private final String ADVETISEMENT_ENTITY = "Anuncios";
    private final String ITEM_ENTITY = "Itens";

    public AdvertisementDAO() {
        super();
        //AdID = getFirebaseReference().child("Anuncios").push().getKey();
    }

    public void saveNewAd(Context context, Advertisement ad){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child(ADVETISEMENT_ENTITY).push().setValue(ad);
            getFirebaseReference().child(ITEM_ENTITY).child("@"+ad.getItem()).setValue(ad.getItem());
            for(Map.Entry<String,String> map : ad.getWishList().entrySet()){
                getFirebaseReference().child(ITEM_ENTITY).child(map.getKey()).setValue(map.getValue());}
        }
        else{
            Toast.makeText(context, (String) "User nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateAdInfo(Advertisement ad, String adID){
        getFirebaseReference().child(ADVETISEMENT_ENTITY).child(adID).child("item").setValue(ad.getItem());
        getFirebaseReference().child(ITEM_ENTITY).child("@"+ad.getItem()).setValue(ad.getItem());
        getFirebaseReference().child(ADVETISEMENT_ENTITY).child(adID).child("description").setValue(ad.getDescription());
        getFirebaseReference().child(ADVETISEMENT_ENTITY).child(adID).child("wishList").setValue(ad.getWishList());
        for(Map.Entry<String,String> map : ad.getWishList().entrySet()){
            getFirebaseReference().child(ITEM_ENTITY).child(map.getKey()).setValue(map.getValue());}
    }

    public void deleteAdvetisement(String adID){
        getFirebaseReference().child(ADVETISEMENT_ENTITY).child(adID).removeValue();
    }

    public DatabaseReference makeFbInstanceReference(){
        return getFirebaseInstance().getReference(ADVETISEMENT_ENTITY);
    }
}
