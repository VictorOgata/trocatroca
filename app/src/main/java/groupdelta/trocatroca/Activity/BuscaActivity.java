package groupdelta.trocatroca.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import groupdelta.trocatroca.DataAccessObject.Conexao;
import groupdelta.trocatroca.Entities.Anuncio;
import groupdelta.trocatroca.Entities.Usuario;
import groupdelta.trocatroca.AdressList;
import groupdelta.trocatroca.R;

import static android.widget.Toast.LENGTH_LONG;

public class BuscaActivity extends AppCompatActivity {
    private EditText editPalavra;
    private ListView Busca;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;

    private List<String> listAnuncio = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterAnuncio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        editPalavra= (EditText) findViewById(R.id.TextSearch);
        Busca=(ListView) findViewById(R.id.ListSearch);

        myRef = FirebaseDatabase.getInstance().getReference("Anuncios");

        eventEdit();


    }

    private void eventEdit(){
        editPalavra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String word = editPalavra.getText().toString().trim();
                BuscaWord(word);

            }
        });

    }
    private void BuscaWord(String word) {
        Query query;
        if (word.equals("") ){
            query = myRef;
        }else{
            query = myRef.orderByChild("item").startAt(word.replace(", ", ",").replace(" ", "_").toUpperCase()).endAt(word.replace(", ", ",").replace(" ", "_").toUpperCase()+"\uf8ff");
        }

        listAnuncio.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    listAnuncio.add((String) objSnapshot.child("item").getValue());
                }
                arrayAdapterAnuncio = new ArrayAdapter<String>(BuscaActivity.this, android.R.layout.simple_list_item_1,listAnuncio);
                Busca.setAdapter(arrayAdapterAnuncio);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void onResume(){
        super.onResume();
        BuscaWord("");
    }
}
