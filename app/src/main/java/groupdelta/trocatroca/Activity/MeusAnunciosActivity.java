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

import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

public class MeusAnunciosActivity  extends  AppCompatActivity {
    private EditText editPalavra;
    private ListView Busca;
    private DatabaseReference myRef;
    private FirebaseDatabase firebaseDatabase;
    private List<String> listAnuncioID = new ArrayList<String>();
    private List<Advertisement> listAdvertisementClasses = new ArrayList<Advertisement>();
    private List<String> listAnuncioNames = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterAnuncio, arrayAdapterAnuncio1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meusanuncios);

        Busca=(ListView) findViewById(R.id.ListSearch);

        myRef = FirebaseDatabase.getInstance().getReference("Anuncios");
        ListaAnuncios();


    }


    private void ListaAnuncios() {
        Query query;
        UserDAO userDao =  new UserDAO();
        String uid = userDao.getFirebaseAuth().getUid();
        query = myRef.orderByChild("host").startAt(uid);

        listAnuncioNames.clear();
        listAnuncioID.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Advertisement p = objSnapshot.getValue(Advertisement.class);
                    String itemP = p.getItem();
                    listAdvertisementClasses.add(p);
                    listAnuncioID.add(objSnapshot.getKey().toString());
                    listAnuncioNames.add(itemP.replace("_", " "));
                }
                arrayAdapterAnuncio = new ArrayAdapter<String>(MeusAnunciosActivity.this, android.R.layout.simple_list_item_1, listAnuncioNames);
                arrayAdapterAnuncio1 = new ArrayAdapter<String>(MeusAnunciosActivity.this, android.R.layout.simple_list_item_1,listAnuncioID);
                Busca.setAdapter(arrayAdapterAnuncio);
                Busca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("IDAnuncio",arrayAdapterAnuncio1.getItem(position).toString());
                        Intent i = new Intent(MeusAnunciosActivity.this, PaginaAnuncioEdit.class);
                        i.putExtras(bundle);
                        startActivity(i);}

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    protected void onResume(){
        super.onResume();
    }


}
