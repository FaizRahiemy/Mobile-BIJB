package com.volunteam.mobilebijb.parking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.DatePickerFragment;

import java.util.Calendar;
import java.util.HashMap;

public class CalculateFragment extends Fragment implements CalculateFragmentPresenter.View, View.OnClickListener{

    private CalculateFragmentPresenter presenter;
    private TextView txCost;
    private Button btnCalculate;
    private EditText edtTglMasuk, edtTglKeluar;
    private EditText edtTimeMasuk, edtTimeKeluar;
    private HashMap<String, String> paramPlan;

    public CalculateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculate_parking, container, false);
        presenter = new CalculateFragmentPresenter(this);

        //init views
        defineViews(v);

        return v;
    }

    private void defineViews(View v){
        paramPlan = new HashMap<>();
        txCost = (TextView)v.findViewById(R.id.tx_bayar_parking);
        edtTglMasuk = v.findViewById(R.id.edt_tglm_parking);
        edtTglKeluar = v.findViewById(R.id.edt_tglk_parking);
        edtTimeMasuk = v.findViewById(R.id.edt_jm_parking);
        edtTimeKeluar = v.findViewById(R.id.edt_jk_parking);
        edtTglMasuk.setOnClickListener(this);
        edtTglKeluar.setOnClickListener(this);
        edtTimeMasuk.setOnClickListener(this);
        edtTimeKeluar.setOnClickListener(this);
        btnCalculate = (Button)v.findViewById(R.id.btn_calculate_parking);
        btnCalculate.setOnClickListener(this);
    }

    @Override
    public void showCost(String cost) {
        txCost.setText(cost);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_calculate_parking:
                paramPlan.put("kendaraan","tes");
                paramPlan.put("tgl_masuk","tes");
                paramPlan.put("jam_masuk","tes");
                paramPlan.put("tgl_keluar","tes");
                paramPlan.put("jam_keluar","tes");
                presenter.calculateParking(paramPlan);
                break;
            case R.id.edt_tglm_parking:
                showTanggalMDialog();
                break;
            case R.id.edt_tglk_parking:
                showTanggalKDialog();
                break;
            case R.id.edt_jm_parking:
                showWaktuMDialog();
                break;
            case R.id.edt_jk_parking:
                showWaktuKDialog();
                break;
        }
    }

    public void showTanggalMDialog() {
        DatePickerFragment date = new DatePickerFragment();
        final Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month>=9) {
                    edtTglMasuk.setText(String.valueOf(year) + "-" + String.valueOf(month + 1)
                            + "-" + String.valueOf(dayOfMonth));
                }else{
                    edtTglMasuk.setText(String.valueOf(year) + "-" + "0" + String.valueOf(month + 1)
                            + "-" + String.valueOf(dayOfMonth));
                }
            }
        };
        date.setCallBack(dateSetListener);
        try{
            date.show(getActivity().getSupportFragmentManager(),"Tanggal Booking");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showTanggalKDialog() {
        DatePickerFragment date = new DatePickerFragment();
        final Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (month>=9) {
                    edtTglKeluar.setText(String.valueOf(year) + "-" + String.valueOf(month + 1)
                            + "-" + String.valueOf(dayOfMonth));
                }else{
                    edtTglKeluar.setText(String.valueOf(year) + "-" + "0" + String.valueOf(month + 1)
                            + "-" + String.valueOf(dayOfMonth));
                }
            }
        };
        date.setCallBack(dateSetListener);
        try{
            date.show(getActivity().getSupportFragmentManager(),"Tanggal Booking");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showWaktuMDialog() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtTimeMasuk.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Waktu Booking");
        mTimePicker.show();
    }

    public void showWaktuKDialog() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtTimeKeluar.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Waktu Booking");
        mTimePicker.show();
    }
}
