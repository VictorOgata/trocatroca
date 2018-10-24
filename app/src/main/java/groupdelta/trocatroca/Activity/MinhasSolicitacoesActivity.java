package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.TradeDAO;
import groupdelta.trocatroca.DataAccessObject.TradeRequestDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.Trade;
import groupdelta.trocatroca.Entities.TradeRequest;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class MinhasSolicitacoesActivity extends AppCompatActivity {

    private TextView reqMate;
    private TextView reqItem;
    private TextView reqMessage;
    private Button btnAccept;

    private TradeRequestDAO tdrDAO;
    private AdvertisementDAO adDAO;
    private UserDAO userDAO;
    private TradeDAO tradeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_solicitacoes);

        reqMate = findViewById(R.id.reqMate);
        reqItem = findViewById(R.id.reqItem);
        reqMessage = findViewById(R.id.reqMessage);
        btnAccept = findViewById(R.id.btnAcceptReq);

        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();


        adDAO = new AdvertisementDAO();
        userDAO = new UserDAO();
        tdrDAO = new TradeRequestDAO();
        tradeDAO = new TradeDAO();

        Query queryReq,queryAd,queryU;

        queryReq=tdrDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(bundle.getString("IDsolicitacao"));
        queryReq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                    showReqData(dataSnapshot,bundle.getString("IDsolicitacao"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        queryAd= adDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(bundle.getString("IDanuncio"));
        queryAd.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren())
                            showAdData(dataSnapshot,bundle.getString("IDanuncio"));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        queryU=userDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(bundle.getString("IDsolicitante"));
        queryU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                    showUData(dataSnapshot,bundle.getString("IDsolicitante"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trade tradeH = new Trade();
                Trade tradeT = new Trade();

                tradeH.sethTrader(userDAO.getFirebaseAuth().getCurrentUser().getUid());
                tradeH.settTrader(bundle.getString("IDsolicitante"));
                tradeH.setAdID(bundle.getString("IDanuncio"));
                tradeH.setStatus("Em andamento");

                tradeT.sethTrader(bundle.getString("IDsolicitante"));
                tradeT.settTrader(userDAO.getFirebaseAuth().getCurrentUser().getUid());
                tradeT.setAdID(bundle.getString("IDanuncio"));
                tradeT.setStatus("Em andamento");

                tradeDAO.saveNewTrade(MinhasSolicitacoesActivity.this,tradeH);
                tradeDAO.saveNewTrade(MinhasSolicitacoesActivity.this,tradeT);

                Intent i = new Intent( MinhasSolicitacoesActivity.this, HomescreenActivity.class);
                startActivity(i);

                tdrDAO.deleteRequest(bundle.getString("IDsolicitacao"));
            }
        });
    }

    private void showUData(DataSnapshot dataSnapshot, String iDsolicitante) {
        User user = dataSnapshot.child(iDsolicitante).getValue(User.class);
        reqMate.setText("Solicitação de: "+user.getNick());
    }

    private void showReqData(DataSnapshot dataSnapshot, String iDsolicitacao) {
        TradeRequest tradeRequest = dataSnapshot.child(iDsolicitacao).getValue(TradeRequest.class);
        reqMessage.setText("Mensagem: "+tradeRequest.getMessage());
    }

    private void showAdData(DataSnapshot dataSnapshot, String iDanuncio) {
        Advertisement advertisement= dataSnapshot.child(iDanuncio).getValue(Advertisement.class);
        reqItem.setText("Item: "+advertisement.getItem().replace("_"," "));
    }
}
