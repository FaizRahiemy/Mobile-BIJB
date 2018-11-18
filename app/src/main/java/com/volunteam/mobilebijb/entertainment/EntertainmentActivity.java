package com.volunteam.mobilebijb.entertainment;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.entertainment.fragments.MovieFragment;
import com.volunteam.mobilebijb.entertainment.fragments.NewsFragment;
import com.volunteam.mobilebijb.entertainment.models.news.Article;
import com.volunteam.mobilebijb.entertainment.models.movie.Result;

import java.util.ArrayList;
import java.util.List;

public class EntertainmentActivity extends AppCompatActivity implements EntertainmentView{

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private NewsFragment newsFragment = new NewsFragment();
    private MovieFragment movieFragment = new MovieFragment();

    private final EntertainmentPresenter entertainmentPresenter = new EntertainmentPresenter(this);
    private final String apiKeyNews = "261d82dd7e26494e841fb1039a4fdaf7";
    private final String apiKeyMovie = "4b9bfb0e83de2a4afb17c157ccb254f3";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        defineViews();
        setToolbar(toolbar);
        setTabLayout(viewPager, tabLayout);
        showProgressDialog();
        newsFragment.setEntertainmentPresenter(entertainmentPresenter);
        movieFragment.setEntertainmentPresenter(entertainmentPresenter);
        entertainmentPresenter.getBerita("id", apiKeyNews);
        entertainmentPresenter.getPopularMovies(apiKeyMovie);
        entertainmentPresenter.getNowPlayingMovies(apiKeyMovie);
    }

    private void defineViews(){
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Entertainment & News");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setTabLayout(ViewPager viewPager, TabLayout tabLayout){
        tabLayout.setupWithViewPager(viewPager, true);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(newsFragment, "Berita");
        adapter.addFragment(movieFragment, "Film");
        viewPager.setAdapter(adapter);
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

    @Override
    public void setBerita(List<Article> articleList) {
        newsFragment.setAdapter(articleList);
        dismissProgressDialog();
        newsFragment.stopSwipeRefresh();
    }

    @Override
    public void setHeadlineBerita(Article headlineBerita) {
        newsFragment.setHeadlineArticle(headlineBerita);
        dismissProgressDialog();
        newsFragment.stopSwipeRefresh();
    }

    @Override
    public void onFailureGetBerita(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
        newsFragment.stopSwipeRefresh();
    }

    @Override
    public void setFilmSortByPopularity(List<Result> filmSortByPopularity) {
        movieFragment.setAdapterMoviePopular(filmSortByPopularity);
        dismissProgressDialog();
        movieFragment.stopRefresh();
    }

    @Override
    public void setFilmSortByNew(List<Result> filmSortByNew) {
        movieFragment.setAdapterMovieNew(filmSortByNew);
        dismissProgressDialog();
        movieFragment.stopRefresh();
    }

    @Override
    public void onFailureGetFilm(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        dismissProgressDialog();
        movieFragment.stopRefresh();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
