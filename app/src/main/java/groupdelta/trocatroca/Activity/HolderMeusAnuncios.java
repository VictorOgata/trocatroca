package groupdelta.trocatroca.Activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import groupdelta.trocatroca.R;

public class HolderMeusAnuncios extends RecyclerView.ViewHolder {

    //views
    ImageView imagemItem;
    TextView meuItem, itemDesej, descricao;

    public HolderMeusAnuncios(@NonNull View itemView) {
        super(itemView);
        this.imagemItem = itemView.findViewById(R.id.Ianuncio_imagem);
        this.meuItem = itemView.findViewById(R.id.Ianuncio_meuItem);
        this.itemDesej = itemView.findViewById(R.id.Ianuncio_itemDesejado);
        this.descricao = itemView.findViewById(R.id.Ianuncio_descricao);
    }
}
