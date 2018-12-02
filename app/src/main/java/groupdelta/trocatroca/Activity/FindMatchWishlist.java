package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

public class FindMatchWishlist extends  AppCompatActivity {

    private ListView myAdList;
    private List<String> listAnuncioID;
    private List<String> listAnuncioNames;
    private ArrayAdapter<String> arrayAdapterAnuncio, arrayAdapterAnuncio1;
    private AdvertisementDAO adDAO;
    private Button btnBuscar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findmatchwishlist);
        myAdList=(ListView) findViewById(R.id.ListMatch);
        btnBuscar = findViewById(R.id.btnFindMatch);
        adDAO= new AdvertisementDAO();
        listAnuncioID = new ArrayList<String>();
        listAnuncioNames = new ArrayList<String>();
        arrayAdapterAnuncio = new ArrayAdapter<String>(FindMatchWishlist.this, android.R.layout.simple_list_item_1, listAnuncioNames);
        arrayAdapterAnuncio1 = new ArrayAdapter<String>(FindMatchWishlist.this, android.R.layout.simple_list_item_1,listAnuncioID);
        myAdList.setAdapter(arrayAdapterAnuncio);
        ListaAnuncios();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayAdapterAnuncio.notifyDataSetChanged();
                arrayAdapterAnuncio1.notifyDataSetChanged();
                listAnuncioNames.clear();
                listAnuncioID.clear();
                ListaAnuncios();

            }
        });

        myAdList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("IDAnuncio",arrayAdapterAnuncio1.getItem(position).toString());
                Intent i = new Intent(FindMatchWishlist.this, PaginaAnuncio.class);
                i.putExtras(bundle);
                startActivity(i);}
        });
    }

    private void ListaAnuncios() {
        UserDAO userDao =  new UserDAO();
        String uid = userDao.getFirebaseAuth().getUid();
        final Query query = adDAO.makeFbInstanceReference().orderByChild("host").equalTo(uid);

        Toast.makeText(FindMatchWishlist.this,"Carregando dados...",Toast.LENGTH_LONG).show();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query1;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    final Advertisement p = objSnapshot.getValue(Advertisement.class);
                    Iterator pIT = p.getWishList().entrySet().iterator();
                    while (pIT.hasNext()){
                        Map.Entry wish = (Map.Entry)pIT.next();
                        query1=adDAO.makeFbInstanceReference().orderByChild("item").equalTo(wish.getValue().toString());
                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                findMatch(dataSnapshot,p.getItem());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void findMatch(DataSnapshot dataSnapshot, String item){
        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
            Advertisement m = objSnapshot.getValue(Advertisement.class);
            if(m.getWishList().containsKey("@"+item)){
                listAnuncioID.add(objSnapshot.getKey().toString());
                listAnuncioNames.add(m.getItem());
                arrayAdapterAnuncio.notifyDataSetChanged();
                arrayAdapterAnuncio1.notifyDataSetChanged();
            }
        }
    }

    protected void onResume(){
        super.onResume();
    }
}
