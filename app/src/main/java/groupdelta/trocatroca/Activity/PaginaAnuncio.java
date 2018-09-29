package groupdelta.trocatroca.Activity;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class PaginaAnuncio extends AppCompatActivity {

    private TextView Item;
    private TextView Description;
    private TextView City;
    private TextView State;
    private TextView Type;
    Advertisement adv;
    AdvertisementDAO advt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_anuncio);
        Item = findViewById(R.id.item);
        Description = findViewById(R.id.description);
        City = findViewById(R.id.city);
        State = findViewById(R.id.state);
        Type = findViewById(R.id.type);
        
    }
}
