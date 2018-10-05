package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class PaginaAnuncioEdit extends AppCompatActivity {
    private TextView Item;
    private TextView Description;
    private TextView Address;
    private TextView Type;
    private TextView Wishes;
    private String IDAd;
    private AdvertisementDAO adDAO;
    private Button btnEditar;
    private Button btnDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_anuncio_edit);
        Item = findViewById(R.id.item);
        Description = findViewById(R.id.description);
        Address = findViewById(R.id.Address);
        Type = findViewById(R.id.type);
        Wishes = findViewById(R.id.wishListEdt);
        btnEditar = findViewById(R.id.btnEditAd);
        btnDeletar = findViewById(R.id.btnDellAd);

        adDAO = new AdvertisementDAO();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String IDanuncio = bundle.getString("IDAnuncio");
        IDAd = IDanuncio;

        adDAO.getFirebaseInstance().getReference("Anuncios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(IDanuncio) != null){
                    showData(dataSnapshot);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Advertisement adInfo = new Advertisement();
                adInfo.setItem(Item.getText().toString());
                adInfo.setDescription(Description.getText().toString());
                String [] wishes = Wishes.getText().toString().replace(", ", ",").replace(" ", "_").toUpperCase().split(",");
                adInfo.setWishList(adInfo.makeWishList(wishes));
                adDAO.updateAdInfo(adInfo,IDAd);
                Intent i = new Intent(PaginaAnuncioEdit.this, HomescreenActivity.class);
                startActivity(i);
                Toast.makeText(PaginaAnuncioEdit.this,"Anuncio alterado com sucesso", LENGTH_LONG).show();
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adDAO.deleteAdvetisement(IDAd);
                Intent i = new Intent(PaginaAnuncioEdit.this, HomescreenActivity.class);
                startActivity(i);
                Toast.makeText(PaginaAnuncioEdit.this,"Anuncio deletado com sucesso", LENGTH_LONG).show();
            }
        });
    }

    private void showData(DataSnapshot ds) {
        if(ds.child(IDAd).hasChildren()) {
            Advertisement adInfo = new Advertisement();
            adInfo.shapeHashMapIntoAdvertisement((HashMap) ds.child(IDAd).getValue());
            Item.setText(adInfo.getItem().replace("_", " "));
            Description.setText(adInfo.getDescription());
            Address.setText("Endereço: " + adInfo.getCity() + ", " + adInfo.getState());
            Type.setText("Tipo: " + adInfo.getType());
            String wishes = "";
            for (Map.Entry<String, String> map : adInfo.getWishList().entrySet()) {
                wishes = wishes + map.getValue().replace("_", " ") + ",";
            }
            Wishes.setText(wishes.substring(0, wishes.length() - 1));
        }
    }
}
