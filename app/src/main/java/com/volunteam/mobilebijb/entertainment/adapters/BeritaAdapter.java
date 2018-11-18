package com.volunteam.mobilebijb.entertainment.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.detailBerita.DetailBeritaActivity;
import com.volunteam.mobilebijb.entertainment.models.news.Article;

import java.util.ArrayList;
import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private List<Article> articleList = new ArrayList<>();

    public BeritaAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_berita, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setBerita(articleList.get(i));
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_berita;
        private TextView title_berita;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_berita = itemView.findViewById(R.id.img_berita);
            title_berita = itemView.findViewById(R.id.title_berita);
        }

        public void setBerita(final Article article){
            Glide.with(itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(img_berita);
            title_berita.setText(article.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Toast.makeText(itemView.getContext(), article.getUrl(), Toast.LENGTH_SHORT).show();*/
                    Intent intentDetailBerita = new Intent(itemView.getContext(), DetailBeritaActivity.class);
                    intentDetailBerita.putExtra("TITLE", article.getTitle());
                    intentDetailBerita.putExtra("LINK", article.getUrl());
                    itemView.getContext().startActivity(intentDetailBerita);
                }
            });
        }
    }
}
