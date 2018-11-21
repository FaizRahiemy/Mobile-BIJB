package com.volunteam.mobilebijb.entertainment.detailMovie;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.models.trailer.Result;

import java.util.List;

public class DetailFilmActivity extends AppCompatActivity implements DetailMovieView{

    private Toolbar toolbar;
    private ImageView img_film;
    private TextView txt_title_film;
    private TextView txt_release_date_film;
    private TextView txt_deskripsi_film;
    private YouTubePlayerSupportFragment youtube_fragment_materi_belajar;

    private ProgressDialog mProgressDialog;

    private final DetailMoviePresenter detailMoviePresenter = new DetailMoviePresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        defineViews();
        showProgressDialog();
        setToolbar(toolbar);
        setContent(getIntentImage(), getIntentTitle(), getIntentReleaseDate(), getIntentDescription());
        detailMoviePresenter.getDetailMovie(getIntentIDMovie(), getApiKeyMovie());
    }

    private void defineViews(){
        toolbar = findViewById(R.id.toolbar);
        img_film = findViewById(R.id.img_film);
        txt_title_film = findViewById(R.id.txt_title_film);
        txt_release_date_film = findViewById(R.id.txt_release_date_film);
        txt_deskripsi_film = findViewById(R.id.txt_deskripsi_film);

        youtube_fragment_materi_belajar = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment_materi_belajar);
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setContent(String image, String title, String releaseDate, String description){
        Glide.with(this)
                .load(image)
                .into(img_film);
        txt_title_film.setText(title);
        txt_release_date_film.setText("Release date: "+releaseDate);
        txt_deskripsi_film.setText(description);
    }

    private String getIntentImage(){
        return getIntent().getStringExtra("IMAGE");
    }

    private String getIntentTitle(){
        return getIntent().getStringExtra("TITLE");
    }

    private String getIntentReleaseDate(){
        return getIntent().getStringExtra("RELEASE_DATE");
    }

    private String getIntentDescription(){
        return getIntent().getStringExtra("DESCRIPTION");
    }

    private String getIntentIDMovie(){
        return getIntent().getStringExtra("ID_MOVIE");
    }

    private String getApiKeyMovie(){
        return getIntent().getStringExtra("API_KEY");
    }

    @Override
    public void setDetailMovie(final List<Result> resultList) {
        YouTubePlayer.OnInitializedListener initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.setPlaybackEventListener(playbackEventListener);
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                youTubePlayer.setOnFullscreenListener(onFullscreenListener);

                if(!b){
                    youTubePlayer.cueVideo(resultList.get(0).getKey());
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("BIJB Mobile","Error Playing Youtube");
            }
        };

        youtube_fragment_materi_belajar.initialize("AIzaSyA6eR-TlNcZPqXstvm6rwL10hHt_yymjcM", initializedListener);
        dismissProgressDialog();
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {
            if(b){

            }
        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };

    private YouTubePlayer.OnFullscreenListener onFullscreenListener = new YouTubePlayer.OnFullscreenListener() {
        @Override
        public void onFullscreen(boolean b) {
            if(b){
                Log.d("BIJB App","Fullscreen Youtube");
            }
        }
    };

    @Override
    public void onFailedGetDetailMovie(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressDialog() {
        dismissProgressDialog();
        mProgressDialog = ProgressDialog.show(this, "", "Loading...");
    }

    private void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
