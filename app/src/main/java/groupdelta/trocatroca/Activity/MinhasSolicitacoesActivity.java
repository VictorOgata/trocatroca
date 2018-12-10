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
import groupdelta.trocatroca.DataAccessObject.ChatDAO;
import groupdelta.trocatroca.DataAccessObject.TradeDAO;
import groupdelta.trocatroca.DataAccessObject.TradeRequestDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.Chat;
import groupdelta.trocatroca.Entities.Trade;
import groupdelta.trocatroca.Entities.TradeRequest;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class MinhasSolicitacoesActivity extends AppCompatActivity {

    private TextView reqMate;
    private TextView reqItem;
    private TextView reqMessage;
    private Button btnAccept;
    private Button btnReject;
    private TradeRequestDAO tdrDAO;
    private AdvertisementDAO adDAO;
    private UserDAO userDAO;
    private TradeDAO tradeDAO;
    private ChatDAO chatDAO;
    private User hostUser;
    private User targetUser;
    private Advertisement mainAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_solicitacoes);

        reqMate = findViewById(R.id.reqMate);
        reqItem = findViewById(R.id.reqItem);
        reqMessage = findViewById(R.id.reqMessage);
        btnAccept = findViewById(R.id.btnAcceptReq);
        btnReject = findViewById(R.id.btnDenyReq);
        Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();


        adDAO = new AdvertisementDAO();
        userDAO = new UserDAO();
        tdrDAO = new TradeRequestDAO();
        tradeDAO = new TradeDAO();
        chatDAO = new ChatDAO();

        Query queryReq,queryAd,queryUT,queryUH;

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

        queryUT=userDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(bundle.getString("IDsolicitante"));
        queryUT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                    showUData(dataSnapshot,bundle.getString("IDsolicitante"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        queryUH=userDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(userDAO.getCurrentUserID());
        queryUH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saveHostUserData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( MinhasSolicitacoesActivity.this, HomescreenActivity.class);
                startActivity(i);

                tdrDAO.deleteRequest(bundle.getString("IDsolicitacao"));
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trade tradeH = new Trade();
                Trade tradeT = new Trade();
                Chat chat = new Chat();
                String chatID;
                chatID = chatDAO.getFirebaseReference().child(chatDAO.getCHAT_ENTITY()).push().getKey();

                tradeH.sethTrader(userDAO.getCurrentUserID());
                tradeH.settTrader(bundle.getString("IDsolicitante"));
                tradeH.setAdID(bundle.getString("IDanuncio"));
                tradeH.setTradeText("Troca do "+mainAD.getType()+" "+mainAD.getItem().replace("_"," ")+" com o usuário "+targetUser.getNick());
                tradeH.setChatID(chatID);

                tradeT.sethTrader(bundle.getString("IDsolicitante"));
                tradeT.settTrader(userDAO.getFirebaseAuth().getCurrentUser().getUid());
                tradeT.setAdID(bundle.getString("IDanuncio"));
                tradeT.setTradeText("Troca do "+mainAD.getType()+" "+mainAD.getItem().replace("_"," ")+" com "+hostUser.getNick());
                tradeT.setChatID(chatID);

                chat.setUserID1(userDAO.getCurrentUserID());
                chat.setUserID2(bundle.getString("IDsolicitante"));
                chat.setAdID(bundle.getString("IDanuncio"));

                tradeDAO.saveNewTrade(MinhasSolicitacoesActivity.this,tradeH);
                tradeDAO.saveNewTrade(MinhasSolicitacoesActivity.this,tradeT);
                chatDAO.saveNewChat(MinhasSolicitacoesActivity.this,chat,chatID);

                Intent i = new Intent( MinhasSolicitacoesActivity.this, HomescreenActivity.class);
                startActivity(i);

                tdrDAO.deleteRequest(bundle.getString("IDsolicitacao"));
            }
        });

    }

    private void saveHostUserData(DataSnapshot dataSnapshot) {
        hostUser = dataSnapshot.child(userDAO.getCurrentUserID()).getValue(User.class);
    }

    private void showUData(DataSnapshot dataSnapshot, String iDsolicitante) {
        targetUser = dataSnapshot.child(iDsolicitante).getValue(User.class);
        reqMate.setText("Solicitação de: "+targetUser.getNick());
    }

    private void showReqData(DataSnapshot dataSnapshot, String iDsolicitacao) {
        TradeRequest tradeRequest = dataSnapshot.child(iDsolicitacao).getValue(TradeRequest.class);
        reqMessage.setText("Mensagem: "+tradeRequest.getMessage());
    }

    private void showAdData(DataSnapshot dataSnapshot, String iDanuncio) {
        mainAD= dataSnapshot.child(iDanuncio).getValue(Advertisement.class);
        reqItem.setText("Item: "+mainAD.getItem().replace("_"," "));
    }
}
