package groupdelta.trocatroca.DataAccessObject;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.TradeRequest;

import static android.widget.Toast.LENGTH_LONG;

public class TradeRequestDAO extends DbConection {
    private final String TRADE_REQUEST_ENTITY = "Solicitacao";

    public TradeRequestDAO() {
        super();
    }

    public void saveNewRequest(Context context, TradeRequest tradeR){
        if (getFirebaseAuth().getCurrentUser() != null) {
            getFirebaseReference().child(TRADE_REQUEST_ENTITY).push().setValue(tradeR);
        }
        else{
            Toast.makeText(context, (String) "Usuario nao autenticado.", LENGTH_LONG).show();
        }
    }

    public void updateRequestInfo(TradeRequest tradeR, String adID){
        getFirebaseReference().child(TRADE_REQUEST_ENTITY).child(adID).setValue(tradeR);
    }

    public void deleteRequest(String adID){
        getFirebaseReference().child(TRADE_REQUEST_ENTITY).child(adID).removeValue();
    }

    public DatabaseReference makeFbInstanceReference(){
        return getFirebaseInstance().getReference(TRADE_REQUEST_ENTITY);
    }
}
