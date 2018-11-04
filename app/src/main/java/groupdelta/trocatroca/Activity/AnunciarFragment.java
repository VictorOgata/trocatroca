package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import groupdelta.trocatroca.R;

public class AnunciarFragment extends Fragment {

    private Button btnNewAd;
    private Button btnMyAd;

    //lista de itens
    RecyclerView rcView;
    AdapterMeusAnuncios adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_anunciar, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        btnNewAd = view.findViewById(R.id.btnNovoAnuncio);
        btnMyAd = view.findViewById(R.id.btnMeusAnuncios);
        btnNewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnunciarFragment.super.getContext(),NovoAnuncioActivity.class);
                startActivity(i);
            }
        });
        btnMyAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnunciarFragment.super.getContext(),MeusAnunciosActivity.class);
                startActivity(i);
            }
        });

        //recyclerview
        rcView = view.findViewById(R.id.recycler_mAnuncios);
        rcView.setLayoutManager(new LinearLayoutManager(AnunciarFragment.super.getContext()));
        //rcView.setLayoutManager(new GridLayoutManager(AnunciarFragment.super.getContext(),2));
        //adapter
        adapter = new AdapterMeusAnuncios(AnunciarFragment.super.getContext(), getItens());
        rcView.setAdapter(adapter);
    }

    //adicionar itens ao ArrayList
    private ArrayList<ItensAnuncios> getItens() {
        ArrayList<ItensAnuncios> itens = new ArrayList<>();

        ItensAnuncios i = new ItensAnuncios();
        i.setImagem(R.drawable.darksouls);
        i.setMeuItem("Dark Souls");
        i.setItemDesejado("Troco por: Tomb Raider 3 - The Lost Artifact");
        itens.add(i);

        i = new ItensAnuncios();
        i.setImagem(R.drawable.darksouls);
        i.setMeuItem("Dark Souls");
        i.setItemDesejado("Troco por: The Last of Us");
        itens.add(i);

        i = new ItensAnuncios();
        i.setImagem(R.drawable.darksouls);
        i.setMeuItem("Dark Souls");
        i.setItemDesejado("Troco por: Call Of Duty - Black Ops");
        itens.add(i);

        i = new ItensAnuncios();
        i.setImagem(R.drawable.darksouls);
        i.setMeuItem("Dark Souls");
        i.setItemDesejado("Troco por: Passar nessa materia");
        itens.add(i);

        i = new ItensAnuncios();
        i.setImagem(R.drawable.darksouls);
        i.setMeuItem("Dark Souls");
        i.setItemDesejado("Troco por: Qualquer coisa");
        itens.add(i);

        return itens;
    }

}
