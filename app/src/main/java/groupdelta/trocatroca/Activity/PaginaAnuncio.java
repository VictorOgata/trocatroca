package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.TradeRequestDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.TradeRequest;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class PaginaAnuncio extends AppCompatActivity {

    private TextView Item;
    private TextView uNick;
    private TextView Description;
    private TextView Address;
    private TextView Type;
    private ListView adWList;
    private EditText RText;
    private Button btnSendR;

    private AdvertisementDAO advtDAO;
    private UserDAO userDAO;
    private TradeRequestDAO tradeRDAO;
    private Advertisement mainAD;

    private String idAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_anuncio);

        uNick = findViewById(R.id.nickAdP);
        Item = findViewById(R.id.itemAdP);
        Description = findViewById(R.id.descAdP);
        Address = findViewById(R.id.addressAdP);
        Type = findViewById(R.id.typeAdP);
        adWList = (ListView) findViewById(R.id.wListAdP);
        RText = findViewById(R.id.otxtAdP);
        btnSendR = findViewById(R.id.btnSendReq);

        advtDAO = new AdvertisementDAO();
        userDAO = new UserDAO();
        tradeRDAO = new TradeRequestDAO();
        mainAD = new Advertisement();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idAD = bundle.getString("IDAnuncio");

        advtDAO.getFirebaseInstance().getReference("Anuncios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null)
                    showData(dataSnapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnSendR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query= userDAO.makeFbInstanceReference()
                        .orderByKey()
                        .equalTo(userDAO.getCurrentUserID());

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User uInfo= dataSnapshot.child(userDAO.getCurrentUserID()).getValue(User.class);
                        TradeRequest tradeR = new TradeRequest();
                        tradeR.setHost(userDAO.getCurrentUserID());
                        tradeR.setTarget(mainAD.getHost());
                        tradeR.setAdID(idAD);
                        tradeR.setRequestText(uInfo.getNick()+" tem interesse no seu "+mainAD.getType()+" "+mainAD.getItem().replace("_"," "));
                        String text = RText.getText().toString();
                        tradeR.setMessage((text.length()>254)? text.substring(0,255) : text);
                        tradeRDAO.saveNewRequest(PaginaAnuncio.this,tradeR);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent i = new Intent( PaginaAnuncio.this, HomescreenActivity.class);
                startActivity(i);
            }
        });
    }

    private void showData(DataSnapshot ds){
        List<String> adWishes= new ArrayList<String>();

        mainAD.shapeHashMapIntoAdvertisement((HashMap)ds.child(idAD).getValue());
        Item.setText("Item: "+mainAD.getItem().replace("_"," "));
        Description.setText("Descrição: "+mainAD.getDescription());
        Address.setText("Endereço: "+mainAD.getCity()+","+mainAD.getState());
        Type.setText("Tipo: "+mainAD.getType());

        for(Map.Entry<String,String> map: mainAD.getWishList().entrySet()) {
            adWishes.add(map.getValue());
        }
        ArrayAdapter<String> wishesAdapter= new ArrayAdapter<String>(PaginaAnuncio.this, android.R.layout.simple_list_item_1,adWishes);
        wishesAdapter.notifyDataSetChanged();
        adWList.setAdapter(wishesAdapter);

        userDAO.getFirebaseInstance().getReference("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnap2) {
                if(dataSnap2!=null) {
                    uNick.setText("Anúncio de: " + dataSnap2.child(mainAD.getHost()).getValue(User.class).getNick());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
