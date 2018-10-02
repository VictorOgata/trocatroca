package groupdelta.trocatroca.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import groupdelta.trocatroca.R;

public class AnunciarFragment extends Fragment {


    private Button btnNewAd;
    private Button btnMyAd;

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
    }
}
