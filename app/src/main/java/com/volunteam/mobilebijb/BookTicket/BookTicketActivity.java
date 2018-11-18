package com.volunteam.mobilebijb.BookTicket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.volunteam.mobilebijb.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookTicketActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_book_ticket)
    Toolbar toolbarBookTicket;
    @BindView(R.id.webviewTicket)
    WebView webviewTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarBookTicket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBookTicket.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String date = getIntent().getStringExtra("date");
        String dep = getIntent().getStringExtra("dep");
        String des = getIntent().getStringExtra("des");

        SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");

        WebSettings webSettings = webviewTicket.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.DATE, 1);
        String url = "https://m.tiket.com/pesawat/search?d=" + dep + "&a=" + des + "&date=" + date + "&adult=1&child=0&infant=0&class=economy&type=depart&dType=airport&aType=airport";
        webviewTicket.loadUrl(url);
    }
}
