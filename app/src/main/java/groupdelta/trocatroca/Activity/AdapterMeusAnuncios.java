package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import groupdelta.trocatroca.R;

public class AdapterMeusAnuncios extends RecyclerView.Adapter<AdapterMeusAnuncios.HolderMeusAnuncios> {

    Context context;
    ArrayList<ItensAnuncios> itens;

    public AdapterMeusAnuncios(Context context, ArrayList<ItensAnuncios> itens) {
        this.context = context;
        this.itens = itens;
    }

    @NonNull
    @Override
    public HolderMeusAnuncios onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //converte xml em objeto view
        //inflate(R.layout.anuncios_itens, null) da erro no layout
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.anuncios_itens, viewGroup, false);
        return new HolderMeusAnuncios(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMeusAnuncios holder, int position) {
        //conecta os dados aos nossos views
        holder.imagemItem.setImageResource(itens.get(position).getImagem());
        holder.meuItem.setText(itens.get(position).getMeuItem());
        holder.itemDesej.setText(itens.get(position).getItemDesejado());
        holder.descricao.setText(itens.get(position).getDescricao());

        //animacao
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

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

}
