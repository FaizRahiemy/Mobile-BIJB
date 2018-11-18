package com.volunteam.mobilebijb.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.home.model.DummyFilm;

import java.util.ArrayList;
import java.util.List;

public class FilmHomeAdapter extends RecyclerView.Adapter<FilmHomeAdapter.ViewHolder>{

    private List<DummyFilm> dummyFilmList = new ArrayList<>();

    public FilmHomeAdapter(List<DummyFilm> dummyFilmList) {
        this.dummyFilmList = dummyFilmList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_home,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setFilm(dummyFilmList.get(i));
    }

    @Override
    public int getItemCount() {
        return dummyFilmList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_film;
        private TextView txt_film;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_film = itemView.findViewById(R.id.img_film);
            txt_film = itemView.findViewById(R.id.txt_film);
        }

        public void setFilm(DummyFilm dummyFilm){
            txt_film.setText(dummyFilm.getTitle());
        }
    }
}
