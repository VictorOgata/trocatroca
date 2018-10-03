package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import groupdelta.trocatroca.Entities.Trade;

import static android.widget.Toast.LENGTH_LONG;

public class TradeDAO extends DbConection {

    private final String TRADE_ENTITY = "Troca";

    public TradeDAO() {
        super();
    }

    public void saveNewRequest(Context context, Trade trade){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child(TRADE_ENTITY).push().setValue(trade);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateAdInfo(Trade trade, String adID){
        getFirebaseReference().child(TRADE_ENTITY).child(adID).setValue(trade);
    }

    public void deleteAdvetisement(String adID){
        getFirebaseReference().child(TRADE_ENTITY).child(adID).removeValue();
    }
}
