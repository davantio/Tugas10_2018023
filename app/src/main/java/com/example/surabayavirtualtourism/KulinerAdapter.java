package com.example.surabayavirtualtourism;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class KulinerAdapter extends RecyclerView.Adapter<KulinerAdapter.KulinerViewHolder>{
    String data1[], data2[];
    int images[];
    Context context;

    public KulinerAdapter(Context ct, String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;
    }

    @NonNull
    @Override
    public KulinerAdapter.KulinerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sarankuliner, parent, false);
        return new KulinerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KulinerAdapter.KulinerViewHolder holder, int position) {
        holder.nama.setText(data1[position]);
        holder.deskripsi.setText(data2[position]);
        holder.foto.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class KulinerViewHolder extends RecyclerView.ViewHolder {
        TextView nama, deskripsi;
        ImageView foto;
        ConstraintLayout itemLayout;
        public KulinerViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_nama);
            deskripsi = itemView.findViewById(R.id.tv_deskripsi);
            foto = itemView.findViewById(R.id.iv_foto);
            itemLayout = itemView.findViewById(R.id.ItemLayout);
        }
    }
}
