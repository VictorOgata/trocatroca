package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.TradeDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.Trade;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class TradeActivity extends AppCompatActivity {
    // .XML VIEW VARS
    private TextView tradeNick;
    private TextView tradeLocal;
    private TextView tradeCInfo;
    private TextView tradeItem;
    private Button btnTradeChat;
    private Button btnTradeRate;
    private Button btnTradeCancel;
    // ENTITY KEYS
    private String targetTraderID;
    private String tradeID;
    private String advertisementID;
    // DATA ACCESS OBJECTS
    private TradeDAO tradeDAO;
    private UserDAO userDAO;
    private AdvertisementDAO advertisementDAO;
    // ACTIVITY INFO CONTAINER
    private Trade tradeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        // .JAVA AND .XML FIELDS CONECTION
        tradeNick = findViewById(R.id.trdNick);
        tradeLocal = findViewById(R.id.trdLocal);
        tradeCInfo = findViewById(R.id.trdCInfo);
        tradeItem = findViewById(R.id.trdItem);
        btnTradeChat = findViewById(R.id.btnTrdChat);
        btnTradeRate = findViewById(R.id.btnTrdFinish);
        btnTradeCancel = findViewById(R.id.btnTrdCancel);

        //SELECTED TRADE DATA RECOVERY
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        targetTraderID = bundle.getString("IDtarget");
        tradeID = bundle.getString("IDtrade");
        advertisementID = bundle.getString("IDadvertisement");

        //DATA ACCESS OBJECTS STANCE
        userDAO = new UserDAO();
        tradeDAO = new TradeDAO();
        advertisementDAO = new AdvertisementDAO();

        //QUERY STANCE AND DECLARATIONS
        Query tradeQuery, userQuery, advertisementQuery;
        userQuery = userDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(targetTraderID);
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showTargetUserData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        advertisementQuery = advertisementDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(advertisementID);
        advertisementQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showAdvertisementData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tradeQuery = tradeDAO.makeFbInstanceReference()
                .orderByKey()
                .equalTo(tradeID);
        tradeQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                saveTradeData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //BUTTON LISTENERS DECLARATIONS
        btnTradeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("IDtrade",tradeID);
                Intent i = new Intent(TradeActivity.this, ChatActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        btnTradeRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( TradeActivity.this, HomescreenActivity.class);
                startActivity(i);

                tradeDAO.deleteTrade(tradeID);
            }
        });
        btnTradeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( TradeActivity.this, HomescreenActivity.class);
                startActivity(i);

                tradeDAO.deleteTrade(tradeID);
            }
        });
    }


    //QUERY DATA RETURN
    private void saveTradeData(DataSnapshot dataSnapshot) {
        tradeData = dataSnapshot.child(tradeID).getValue(Trade.class);
    }

    private void showAdvertisementData(DataSnapshot dataSnapshot) {
        Advertisement advertisement = dataSnapshot.child(advertisementID).getValue(Advertisement.class);
        tradeItem.setText(advertisement.getType()+": "+advertisement.getItem());
    }

    private void showTargetUserData(DataSnapshot dataSnapshot) {
        User uInfo = dataSnapshot.child(targetTraderID).getValue(User.class);
        tradeNick.setText("Nick: "+uInfo.getNick());
        tradeLocal.setText("Local: "+uInfo.getCity()+", "+uInfo.getState());
        tradeCInfo.setText("Informação de contato:\n"+uInfo.getCInfo());
    }
}
