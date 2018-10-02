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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
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
                
            }
        });
    }

    private void showData(DataSnapshot ds){
        final Advertisement adInfo = new Advertisement();
        List<String> adWishes= new ArrayList<String>();

        adInfo.shapeHashMapIntoAdvertisement((HashMap)ds.child(idAD).getValue());
        Item.setText("Item: "+adInfo.getItem());
        Description.setText("Descrição: "+adInfo.getDescription());
        Address.setText("Endereço: "+adInfo.getCity()+","+adInfo.getState());
        Type.setText("Tipo: "+adInfo.getType());

        for(Map.Entry<String,String> map: adInfo.getWishList().entrySet()) {
            adWishes.add(map.getValue());
        }
        ArrayAdapter<String> wishesAdapter= new ArrayAdapter<String>(PaginaAnuncio.this, android.R.layout.simple_list_item_1,adWishes);
        wishesAdapter.notifyDataSetChanged();
        adWList.setAdapter(wishesAdapter);

        userDAO.getFirebaseInstance().getReference("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnap2) {
                if(dataSnap2!=null) {
                    uNick.setText("Anúncio de: " + dataSnap2.child(adInfo.getHost()).getValue(User.class).getNick());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
