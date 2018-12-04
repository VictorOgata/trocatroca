package groupdelta.trocatroca.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class AdapterMatch extends RecyclerView.Adapter<AdapterMatch.HolderMatch> {
    Context context;
    ArrayList<ItensMatch> itens;
    ArrayList<String> id;

    public AdapterMatch(Context context, ArrayList<ItensMatch> itens, ArrayList<String> id) {
        this.context = context;
        this.itens = itens;
        this.id = id;
    }

    @NonNull
    @Override
    public AdapterMatch.HolderMatch onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_itens, viewGroup, false);
        return new HolderMatch(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMatch.HolderMatch holder, int position) {
        //conecta os dados aos nossos views
        holder.imagem1.setImageResource(itens.get(position).getImagem1());
        holder.imagem2.setImageResource(itens.get(position).getImagem2());
        holder.meuItem.setText(itens.get(position).getMeuItem());
        holder.itemDesej.setText(itens.get(position).getItemDesejado());
        holder.usuario1.setText(itens.get(position).getUsuario1());
        holder.usuario2.setText(itens.get(position).getUsuario2());

        //animacao
        //Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        //holder.itemView.startAnimation(animation);
        holder.itemView.setTag(id.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("IDAnuncio", (String) view.getTag());
            Intent i = new Intent(context, PaginaAnuncio.class);
            i.putExtras(bundle);
            context.startActivity(i);
        }
    };

   public class HolderMatch extends RecyclerView.ViewHolder {
       //views
       ImageView imagem1,imagem2;
       TextView meuItem, itemDesej, usuario1, usuario2;

       public HolderMatch(@NonNull View itemView) {
           super(itemView);
           this.imagem1 = itemView.findViewById(R.id.image_meuitem);
           this.imagem2 = itemView.findViewById(R.id.image_itemdesejado);
           this.usuario1 = itemView.findViewById(R.id.usuario1);
           this.usuario2 = itemView.findViewById(R.id.usuario2);
           this.meuItem = itemView.findViewById(R.id.item1);
           this.itemDesej = itemView.findViewById(R.id.item2);
       }
   }
}
