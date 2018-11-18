package com.volunteam.mobilebijb.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.home.model.DummyBerita;

import java.util.ArrayList;
import java.util.List;

public class BeritaHomeAdapter extends RecyclerView.Adapter<BeritaHomeAdapter.ViewHolder>{
    private List<DummyBerita> dummyBeritaList = new ArrayList<>();

    public BeritaHomeAdapter(List<DummyBerita> dummyBeritaList) {
        this.dummyBeritaList = dummyBeritaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_berita_home,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setBerita(dummyBeritaList.get(i));
    }

    @Override
    public int getItemCount() {
        return dummyBeritaList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_berita;
        private TextView txt_berita;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_berita = itemView.findViewById(R.id.img_berita);
            txt_berita = itemView.findViewById(R.id.txt_berita);
        }
        public void setBerita(DummyBerita dummyBerita){
            txt_berita.setText(dummyBerita.getTitle());
        }
    }
}
