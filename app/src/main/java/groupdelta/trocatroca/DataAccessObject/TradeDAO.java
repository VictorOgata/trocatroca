package groupdelta.trocatroca.DataAccessObject;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import groupdelta.trocatroca.Entities.Trade;

import static android.widget.Toast.LENGTH_LONG;

public class TradeDAO extends DbConection {

    private final String TRADE_ENTITY = "Troca";

    public TradeDAO() {
        super();
    }

    public void saveNewTrade(Context context, Trade trade){
        if (getFirebaseAuth().getCurrentUser() != null) {
            trade.setStatus("inProgress");
            getFirebaseReference().child(TRADE_ENTITY).push().setValue(trade);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateTradeInfo(Trade trade, String adID){
        getFirebaseReference().child(TRADE_ENTITY).child(adID).setValue(trade);
    }

    public void deleteTrade(String adID){
        getFirebaseReference().child(TRADE_ENTITY).child(adID).removeValue();
    }

    public DatabaseReference makeFbInstanceReference(){
        return getFirebaseInstance().getReference(TRADE_ENTITY);
    }
}
