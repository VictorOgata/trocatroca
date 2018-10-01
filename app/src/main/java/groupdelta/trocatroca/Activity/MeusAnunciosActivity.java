package groupdelta.trocatroca.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private DatabaseReference myRef;
    private ListView Busca;
    private FirebaseDatabase firebaseDatabase;
    private List<Advertisement> listAdvertisementClasses = new ArrayList<Advertisement>();
    private List<String> listAnuncioNames = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterAnuncio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meusanuncios);
        Busca = findViewById(R.id.ListSearch);

        myRef = FirebaseDatabase.getInstance().getReference("Usuarios");
        AdapterView<?> parent;

    }

    private void ListaAnuncios() {
        Query query;
        UserDAO userDao =  new UserDAO();
        String uid = userDao.getFirebaseAuth().getUid();
        query = myRef.orderByChild("host").startAt(uid);

        listAnuncioNames.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Advertisement p = objSnapshot.getValue(Advertisement.class);
                    String itemP = p.getItem();
                    listAdvertisementClasses.add(p);
                    listAnuncioNames.add(itemP.replace("_", " "));
                }
                arrayAdapterAnuncio = new ArrayAdapter<String>(MeusAnunciosActivity.this, android.R.layout.simple_list_item_1, listAnuncioNames);
                Busca.setAdapter(arrayAdapterAnuncio);
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
