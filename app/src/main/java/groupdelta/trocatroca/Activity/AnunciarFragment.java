package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import groupdelta.trocatroca.DataAccessObject.AdvertisementDAO;
import groupdelta.trocatroca.DataAccessObject.UserDAO;
import groupdelta.trocatroca.Entities.Advertisement;
import groupdelta.trocatroca.R;

public class AnunciarFragment extends Fragment {

    private FloatingActionButton fabNovoAnuncio;
    //lista de itens
    RecyclerView rcView;
    AdapterMeusAnuncios adapter;

    private List<Advertisement> listAdvertisementClasses = new ArrayList<Advertisement>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anunciar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        fabNovoAnuncio = (FloatingActionButton) view.findViewById(R.id.fabNovoAnuncio);
        fabNovoAnuncio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(AnunciarFragment.super.getContext(), NovoAnuncioActivity.class);
                    startActivity(i);
                }
            });


        //recyclerview
        rcView = view.findViewById(R.id.recycler_mAnuncios);
        rcView.setLayoutManager(new LinearLayoutManager(AnunciarFragment.super.getContext()));
        //rcView.setLayoutManager(new GridLayoutManager(AnunciarFragment.super.getContext(),2));

        //adapter deve ser chamado dentro de getItens()
        getItens();
        //adapter = new AdapterMeusAnuncios(AnunciarFragment.super.getContext(), getItens());
        //rcView.setAdapter(adapter);
    }

    //adicionar itens ao ArrayList
    private void getItens() {
        final ArrayList<ItensAnuncios> itens = new ArrayList<>();
        final ArrayList<String> anuncioID = new ArrayList<>();
        final ArrayList<String> itensDesejados = new ArrayList<>();

        //buscando dados no firebase
        UserDAO userDao =  new UserDAO();
        AdvertisementDAO adDAO= new AdvertisementDAO();
        String uid = userDao.getFirebaseAuth().getUid();
        Query query = adDAO.getFirebaseInstance().getReference("Anuncios").orderByChild("host").equalTo(uid);

        itens.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Advertisement p = objSnapshot.getValue(Advertisement.class);

                    //pega informacoes do anuncio no bd
                    String nome = p.getItem();
                    String descricao = p.getDescription();
                    Map<String, String> itemDesejado = p.getWishList();

                    listAdvertisementClasses.add(p); //?????
                    anuncioID.add(objSnapshot.getKey().toString()); //????????

                    //adiciona informacoes ao arraylist
                    ItensAnuncios i = new ItensAnuncios();
                    i.setMeuItem(nome.replace("_", " ")); //nome meu item
                    i.setImagem(R.drawable.darksouls);
                    i.setDescricao("Descrição: " + descricao);
                    //itens desejads estao em um hashmap
                    itensDesejados.clear();
                    for (String key : itemDesejado.keySet()){
                        String aux = itemDesejado.get(key);
                        aux = aux.toLowerCase().replace("_", " ");
                        aux = aux.substring(0,1).toUpperCase()+aux.substring(1);
                        itensDesejados.add(aux);
                    }
                    i.setItemDesejado("Troco por: " +
                            itensDesejados.toString()
                            .replace("[", "")
                            .replace("]", ""));
                    itens.add(i);
                }

                //chamando adapter
                adapter = new AdapterMeusAnuncios(AnunciarFragment.super.getContext(), itens, anuncioID);
                rcView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
