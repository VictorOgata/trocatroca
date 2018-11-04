package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.TradeDAO;
import groupdelta.trocatroca.DataAccessObject.TradeRequestDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.Trade;
import groupdelta.trocatroca.Entities.TradeRequest;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class    MinhasTrocas extends AppCompatActivity {

    private ListView myTList;
    private ListView myRList;

    private UserDAO userDAO;
    private AdvertisementDAO adDAO;
    private TradeDAO tradeDAO;
    private TradeRequestDAO tradeReqDAO;
    //private String idAD;
    private List<String> tradeInfoList= new ArrayList<String>();
    private List<String> requestInfoList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_trocas);
        myTList = (ListView) findViewById(R.id.myTradeList);
        myRList = (ListView) findViewById(R.id.myRequestList);

        userDAO = new UserDAO();
        adDAO = new AdvertisementDAO();
        tradeDAO = new TradeDAO();
        tradeReqDAO = new TradeRequestDAO();
        //Intent intent = getIntent();
        //Bundle bundle = intent.getExtras();
        //idAD = bundle.getString("IDAnuncio");
        Query query1,query2;

        query1 = tradeDAO.makeFbInstanceReference()
                .orderByChild("hTrader")
                .equalTo(userDAO.getFirebaseAuth().getUid());
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren())
                    showTradeData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        query2 = tradeReqDAO.makeFbInstanceReference()
                .orderByChild("target")
                .equalTo(userDAO.getFirebaseAuth().getUid());
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    showRequestData(dataSnapshot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    private void showTradeData(DataSnapshot ds){
        List<String> tradeTextList = new ArrayList<String>();
        tradeInfoList.clear();

        for(DataSnapshot snap: ds.getChildren()){

            Trade trade= snap.getValue(Trade.class);
            tradeInfoList.add(snap.getKey()+" "+trade.gettTrader()+" "+trade.getAdID());
            tradeTextList.add(trade.getTradeText());
        }

        ArrayAdapter<String> tradeTextListAdapter =
                new ArrayAdapter<String>(MinhasTrocas.this,android.R.layout.simple_list_item_1,tradeTextList);
        final ArrayAdapter<String> tradeInfoListAdapter =
                new ArrayAdapter<String>(MinhasTrocas.this,android.R.layout.simple_list_item_1,tradeInfoList);
        
        tradeTextListAdapter.notifyDataSetChanged();
        tradeInfoListAdapter.notifyDataSetChanged();
        
        myTList.setAdapter(tradeTextListAdapter);
        
        myTList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String [] ids=tradeInfoListAdapter.getItem(position).toString().split(" ");
                Bundle bundle = new Bundle();
                bundle.putString("IDtrade",ids[0]);
                bundle.putString("IDtarget",ids[1]);
                bundle.putString("IDadvertisement",ids[2]);
                Intent i = new Intent(MinhasTrocas.this, TradeActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }


    private void showRequestData(DataSnapshot ds){
        List<String> requestTextList = new ArrayList<String>();
        requestInfoList.clear();

        for(DataSnapshot snap: ds.getChildren()){

            TradeRequest tradeRequest= snap.getValue(TradeRequest.class);
            requestInfoList.add(snap.getKey()+" "+tradeRequest.getHost()+" "+tradeRequest.getAdID());
            requestTextList.add(tradeRequest.getRequestText());

        }

        ArrayAdapter<String> requestTextListAdapter =
                new ArrayAdapter<String>(MinhasTrocas.this,android.R.layout.simple_list_item_1,requestTextList);
        final ArrayAdapter<String> requestInfoListAdapter =
                new ArrayAdapter<String>(MinhasTrocas.this,android.R.layout.simple_list_item_1,requestInfoList);

        requestTextListAdapter.notifyDataSetChanged();
        requestInfoListAdapter.notifyDataSetChanged();

        myRList.setAdapter(requestTextListAdapter);
        myRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String [] ids=requestInfoListAdapter.getItem(position).toString().split(" ");
                Bundle bundle = new Bundle();
                bundle.putString("IDsolicitacao",ids[0]);
                bundle.putString("IDsolicitante",ids[1]);
                bundle.putString("IDanuncio",ids[2]);
                Intent i = new Intent(MinhasTrocas.this, MinhasSolicitacoesActivity.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}
