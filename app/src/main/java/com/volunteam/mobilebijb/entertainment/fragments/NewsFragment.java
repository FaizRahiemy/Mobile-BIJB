package com.volunteam.mobilebijb.entertainment.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.EntertainmentPresenter;
import com.volunteam.mobilebijb.entertainment.adapters.BeritaAdapter;
import com.volunteam.mobilebijb.entertainment.detailBerita.DetailBeritaActivity;
import com.volunteam.mobilebijb.entertainment.models.news.Article;

import java.util.List;

public class NewsFragment extends Fragment {

    private TextView txt_title_headline;
    private ImageView img_headline;
    private TextView txt_isi_headline;
    private TextView btn_bacaselengkapnya;
    private RecyclerView recycler_berita;

    private SwipeRefreshLayout swipe_layout;

    private EntertainmentPresenter entertainmentPresenter;

    public NewsFragment() {

    }

    public void setEntertainmentPresenter(EntertainmentPresenter entertainmentPresenter) {
        this.entertainmentPresenter = entertainmentPresenter;
    }

    public void setAdapter(List<Article> articleListAdapter){
        BeritaAdapter beritaAdapter = new BeritaAdapter(articleListAdapter);
        RecyclerView.LayoutManager layoutManagerOption = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_berita.setLayoutManager(layoutManagerOption);
        recycler_berita.setItemAnimator(new DefaultItemAnimator());
        recycler_berita.setAdapter(beritaAdapter);
        System.out.println("jumlah berita: "+String.valueOf(beritaAdapter.getItemCount()));
    }

    public void setHeadlineArticle(final Article article){
        txt_title_headline.setText(article.getTitle());
        txt_isi_headline.setText(article.getContent());
        Glide.with(getContext())
                .load(article.getUrlToImage())
                .into(img_headline);

        txt_isi_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), article.getUrl(), Toast.LENGTH_SHORT).show();*/
                goToDetailBerita(article);
            }
        });

        txt_title_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), article.getUrl(), Toast.LENGTH_SHORT).show();*/
                goToDetailBerita(article);
            }
        });

        img_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), article.getUrl(), Toast.LENGTH_SHORT).show();*/
                goToDetailBerita(article);
            }
        });
        btn_bacaselengkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getContext(), article.getUrl(), Toast.LENGTH_SHORT).show();*/
                goToDetailBerita(article);
            }
        });
    }

    private void goToDetailBerita(Article article){
        Intent intentDetailBerita = new Intent(getContext(), DetailBeritaActivity.class);
        intentDetailBerita.putExtra("TITLE", article.getTitle());
        intentDetailBerita.putExtra("LINK", article.getUrl());
        startActivity(intentDetailBerita);
    }

    public void stopSwipeRefresh(){
        if (swipe_layout.isRefreshing()){
            swipe_layout.setRefreshing(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txt_title_headline = view.findViewById(R.id.txt_title_headline);
        img_headline = view.findViewById(R.id.img_headline);
        txt_isi_headline = view.findViewById(R.id.txt_isi_headline);
        btn_bacaselengkapnya = view.findViewById(R.id.btn_bacaselengkapnya);
        recycler_berita = view.findViewById(R.id.recycler_berita);

        swipe_layout = view.findViewById(R.id.swipe_layout);
        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                entertainmentPresenter.getBerita("id", "261d82dd7e26494e841fb1039a4fdaf7");
            }
        });
    }
}
