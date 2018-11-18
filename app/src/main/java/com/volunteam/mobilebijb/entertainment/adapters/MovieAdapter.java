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
import com.volunteam.mobilebijb.entertainment.detailMovie.DetailFilmActivity;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Result> movieList = new ArrayList<>();

    public MovieAdapter(List<Result> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.setMovieToAdapter(movieList.get(i));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_movie;
        private TextView txt_title_movie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_movie = itemView.findViewById(R.id.img_movie);
            txt_title_movie = itemView.findViewById(R.id.txt_title_movie);
        }

        public void setMovieToAdapter(final Result movie){
            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w185"+movie.getPosterPath())
                    .into(img_movie);
            txt_title_movie.setText(movie.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetailMovie = new Intent(v.getContext(), DetailFilmActivity.class);
                    intentDetailMovie.putExtra("IMAGE", "http://image.tmdb.org/t/p/w185"+movie.getPosterPath());
                    intentDetailMovie.putExtra("TITLE", movie.getTitle());
                    intentDetailMovie.putExtra("RELEASE_DATE", movie.getReleaseDate());
                    intentDetailMovie.putExtra("DESCRIPTION", movie.getOverview());
                    intentDetailMovie.putExtra("ID_MOVIE", String.valueOf(movie.getId()));
                    intentDetailMovie.putExtra("API_KEY", "4b9bfb0e83de2a4afb17c157ccb254f3");
                    v.getContext().startActivity(intentDetailMovie);
                }
            });
        }
    }
}
