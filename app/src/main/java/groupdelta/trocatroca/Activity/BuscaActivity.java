package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

public class BuscaActivity extends AppCompatActivity {
    private EditText editPalavra;
    private ListView Busca;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private List<Advertisement> listAdvertisementClasses = new ArrayList<Advertisement>();
    private List<String> listAnuncioNames = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterAnuncio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca);

        editPalavra= (EditText) findViewById(R.id.TextSearch);
        Busca=(ListView) findViewById(R.id.ListSearch);

        myRef = FirebaseDatabase.getInstance().getReference("Anuncios");
        AdapterView<?> parent;
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

        listAnuncioNames.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Advertisement p = objSnapshot.getValue(Advertisement.class);
                    String itemP = p.getItem();
                    listAdvertisementClasses.add(p);
                    listAnuncioNames.add(itemP.replace("_"," "));
                }
                arrayAdapterAnuncio = new ArrayAdapter<String>(BuscaActivity.this, android.R.layout.simple_list_item_1,listAnuncioNames);
                Busca.setAdapter(arrayAdapterAnuncio);

                Busca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(BuscaActivity.this, PaginaAnuncio.class);
                        startActivity(i);
                    }
                });
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
