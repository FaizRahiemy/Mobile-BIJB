package com.volunteam.mobilebijb.BookTicket;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.volunteam.mobilebijb.BookTicket.model.AirportsItem;
import com.volunteam.mobilebijb.BookTicket.model.GetBandaraResponse;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickDateActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_book_ticket)
    Toolbar toolbarBookTicket;
    @BindView(R.id.bandara_awal)
    Spinner bandaraAwal;
    @BindView(R.id.switch_bandara)
    ImageView switchBandara;
    @BindView(R.id.bandara_tujuan)
    Spinner bandaraTujuan;
    @BindView(R.id.pick_date_ticket)
    EditText pickDateTicket;
    @BindView(R.id.button_cari_tiket)
    Button buttonCariTiket;


    private DatePicker datePicker;
    private Calendar calendar;
    API api;
    ArrayList<AirportsItem> listBandara;
    ArrayAdapter<CharSequence> adapter;
    int posisi = 0;

    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarBookTicket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBookTicket.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        api = MainAPIHelper.getClient("").create(API.class);

        listBandara = new ArrayList<>();
        adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_dropdown_item,new ArrayList<CharSequence>());
        createBandara();
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bandaraAwal.setAdapter(adapter);
        bandaraTujuan.setAdapter(adapter);
        bandaraAwal.setEnabled(false);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dpDialog = new DatePickerDialog(this, myDateListener, year, month-1, day);
            dpDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
            return dpDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        pickDateTicket.setText(new StringBuilder().append(day).append("-")
                .append(month).append("-").append(year));
    }

    @OnClick({R.id.switch_bandara, R.id.pick_date_ticket, R.id.button_cari_tiket})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch_bandara:
                if (posisi == 0){
                    bandaraAwal.setEnabled(true);
                    bandaraTujuan.setEnabled(false);
                    bandaraTujuan.setSelection(0);
                    posisi = 1;
                }else{
                    bandaraTujuan.setEnabled(true);
                    bandaraAwal.setEnabled(false);
                    bandaraAwal.setSelection(0);
                    posisi = 0;
                }
                break;
            case R.id.pick_date_ticket:
                showDialog(999);
                break;
            case R.id.button_cari_tiket:
                Intent intent = new Intent(getApplicationContext(), BookTicketActivity.class);
                String date = year+"-"+month+"-"+day;
                String dep = listBandara.get(bandaraAwal.getSelectedItemPosition()).getCode();
                String des = listBandara.get(bandaraTujuan.getSelectedItemPosition()).getCode();
                intent.putExtra("date", date);
                intent.putExtra("dep", dep);
                intent.putExtra("des", des);
                startActivity(intent);
                break;
        }
    }

    public void createBandara(){
        api.getBandara(MainAPIHelper.key).enqueue(new Callback<GetBandaraResponse>() {
            @Override
            public void onResponse(Call<GetBandaraResponse> call, Response<GetBandaraResponse> response) {
                if (response.body().getStatusCode() == 1){
                    listBandara.addAll(response.body().getAirports());
                    for (int i=0; i < listBandara.size(); i++){
                        adapter.add(listBandara.get(i).getName() + " (" + listBandara.get(i).getCode() + ")");
                    }
                }else{
                    Toast.makeText(PickDateActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetBandaraResponse> call, Throwable t) {
                Toast.makeText(PickDateActivity.this, "Periksa koneksi internet anda!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
