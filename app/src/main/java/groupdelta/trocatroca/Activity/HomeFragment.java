package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.Entities.User;
import groupdelta.trocatroca.R;

public class HomeFragment extends Fragment {

    private Button btnBusca;
    private Button btnMinhasTrocas;

    RecyclerView rcvMatch;
    AdapterMatch adapterM;
    private SwipeRefreshLayout swipeM;
    private ArrayList<ItensMatch> itens;
    private ArrayList<String> itensID;
    private AdvertisementDAO adDAO;
    private UserDAO userDAO; //usuario1?

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnBusca = view.findViewById(R.id.btnBusca);
        btnMinhasTrocas = view.findViewById(R.id.btnMyTrades);

        btnBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragment.super.getContext(), BuscaActivity.class);
                startActivity(i);
            }
        });

        btnMinhasTrocas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragment.super.getContext(), MinhasTrocas.class);
                startActivity(i);
            }
        });

        itens = new ArrayList<>();
        itensID = new ArrayList<>();
        adDAO= new AdvertisementDAO();
        userDAO = new UserDAO();
        rcvMatch = view.findViewById(R.id.recycler_mMatchs);
        rcvMatch.setLayoutManager(new LinearLayoutManager(HomeFragment.super.getContext()));
        //getItens();

        adapterM = new AdapterMatch(HomeFragment.super.getContext(), itens, itensID);
        rcvMatch.setAdapter(adapterM);

        getItensDB();

        swipeM = (SwipeRefreshLayout) view.findViewById(R.id.swipeMatch);
        swipeM.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //colocar aqui o que vai fazer a cada swipe
                adapterM.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeM.setRefreshing(false);
                    }
                },1000);
            }
        });
    }

    private void getItensDB(){
        //buscando dados no firebase
        UserDAO userDao =  new UserDAO();
        String uid = userDao.getFirebaseAuth().getUid();
        final Query query = adDAO.makeFbInstanceReference().orderByChild("host").equalTo(uid);

        Toast.makeText(HomeFragment.super.getContext(),"Carregando dados...",Toast.LENGTH_LONG).show();

        itens.clear();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query1;
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    //p sao os meus itens
                    final Advertisement p = objSnapshot.getValue(Advertisement.class);

                    Iterator pIT = p.getWishList().entrySet().iterator();

                    while (pIT.hasNext()){
                        Map.Entry wish = (Map.Entry)pIT.next();
                        query1=adDAO.makeFbInstanceReference().orderByChild("item").equalTo(wish.getValue().toString());
                        query1.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                                    //m sao os itens do usuario 2
                                    final Advertisement m = objSnapshot.getValue(Advertisement.class);
                                    final ItensMatch i = new ItensMatch();

                                    if(m.getWishList().containsKey("@"+p.getItem())){
                                        //adiciona informacoes ao arraylist
                                        i.setMeuItem(p.getItem().replace("_"," "));
                                        i.setImagem1(R.drawable.darksouls);
                                        i.setImagem2(R.drawable.darksouls);
                                        i.setItemDesejado(m.getItem().replace("_", " "));
                                        i.setUsuario1("Você");

                                        //buscando nome do usuario2
                                        userDAO.getFirebaseInstance().getReference("Usuarios").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnap2) {
                                                if(dataSnap2!=null) {
                                                    i.setUsuario2(dataSnap2.child(m.getHost()).getValue(User.class).getNick());
                                                    adapterM.notifyDataSetChanged();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });

                                        itens.add(i);
                                        itensID.add(objSnapshot.getKey().toString());
                                        adapterM.notifyDataSetChanged();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {   }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
