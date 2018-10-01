package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

public class PaginaAnuncioEdit extends AppCompatActivity {
    private DatabaseReference myRef;
    private TextView Item;
    private TextView Description;
    private TextView City;
    private TextView State;
    private TextView Type;
    AdvertisementDAO advt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_anuncio_edit);
        Item = findViewById(R.id.item);
        Description = findViewById(R.id.description);
        City = findViewById(R.id.city);
        State = findViewById(R.id.state);
        Type = findViewById(R.id.type);
        myRef = FirebaseDatabase.getInstance().getReference("Anuncios");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String IDanuncio = bundle.getString("IDAnuncio");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Item.setText(dataSnapshot.child(IDanuncio).getValue(Advertisement.class).getItem());
                Description.setText(dataSnapshot.child(IDanuncio).getValue(Advertisement.class).getDescription());
                City.setText(dataSnapshot.child(IDanuncio).getValue(Advertisement.class).getCity());
                State.setText(dataSnapshot.child(IDanuncio).getValue(Advertisement.class).getState());
                Type.setText(dataSnapshot.child(IDanuncio).getValue(Advertisement.class).getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }


}
