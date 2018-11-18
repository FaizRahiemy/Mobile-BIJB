package com.volunteam.mobilebijb.parking;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.DatePickerFragment;
import com.volunteam.mobilebijb.config.TinyDB;
import com.volunteam.mobilebijb.profile.ProfileActivity;

import java.util.Calendar;

public class BookingFragment extends Fragment implements View.OnClickListener,BookingFragmentPresenter.View{

    private Button btnBook;
    private EditText edtTanggalBook, edtJam, edtKendaraan, edtParkir, edtPlat;
    private TextInputLayout txInputTgl, txInputJam, txInputKend, txInpurPark, txInputPlat;
    private ProgressBar progressBar;
    private BookingFragmentPresenter presenter;
    private TinyDB tinyDB;
    private String token = "";
    private String id = "";

    public BookingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_parking, container, false);

        // get id and token from sharedpreference
        tinyDB = new TinyDB(getContext());
        try{
            token = tinyDB.getString("token");
            id = tinyDB.getString("id");
        }catch (Exception e){
            Toast.makeText(getContext(),"There is an error, please re-login",
                    Toast.LENGTH_SHORT).show();
        }
        presenter = new BookingFragmentPresenter(this);

        // init views
        defineViews(v);

        return v;
    }

    private void defineViews(View v){
        btnBook = (Button)v.findViewById(R.id.btn_book_parking);
        edtTanggalBook = (EditText)v.findViewById(R.id.edt_tgl_parking);
        edtJam = (EditText)v.findViewById(R.id.edt_jam_parking);
        edtKendaraan = (EditText)v.findViewById(R.id.edt_kendaraan_parking);
        edtParkir = (EditText)v.findViewById(R.id.edt_parkir);
        edtPlat = (EditText)v.findViewById(R.id.edt_plat);
        txInputTgl = v.findViewById(R.id.txinput_tgl_parking);
        txInputJam = v.findViewById(R.id.txinput_jam_parking);
        txInputKend = v.findViewById(R.id.txinput_kendaraan_parking);
        txInpurPark = v.findViewById(R.id.txinput_parkir);
        txInputPlat = v.findViewById(R.id.txinput_plat);
        progressBar = (ProgressBar)v.findViewById(R.id.pb_loading);
        btnBook.setOnClickListener(this);
        edtTanggalBook.setOnClickListener(this);
        edtJam.setOnClickListener(this);
        edtTanggalBook.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showTanggalDialog();
                }
            }
        });
        edtJam.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    showWaktuDialog();
                }
            }
        });
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_book_parking:
                if (!isErrorInput()){
                    presenter.addBooking(id, token, new Parkir(
                            (edtTanggalBook.getText().toString()+" "+edtJam.getText().toString()),
                            edtKendaraan.getText().toString(),
                            edtParkir.getText().toString(),
                            edtPlat.getText().toString()
                    ));
                }
                break;
            case R.id.edt_tgl_parking:
                // show date picker
                showTanggalDialog();
                break;
            case R.id.edt_jam_parking:
                // show time picker
                System.out.println("masuk time dialog");
                showWaktuDialog();
                break;
        }
    }

    @Override
    public void showTanggalDialog() {
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
                    edtTanggalBook.setText(String.valueOf(year) + "-" + String.valueOf(month + 1)
                            + "-" + String.valueOf(dayOfMonth));
                }else{
                    edtTanggalBook.setText(String.valueOf(year) + "-" + "0" + String.valueOf(month + 1)
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

    @Override
    public void showWaktuDialog() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtJam.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Waktu Booking");
        mTimePicker.show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearField() {
        edtTanggalBook.setText("");
        edtJam.setText("");
        edtKendaraan.setText("");
        edtParkir.setText("");
        edtPlat.setText("");
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void setErrorMessage(TextInputLayout txInput, String message){
        txInput.setErrorEnabled(true);
        txInput.setError(message);
    }

    private boolean isErrorInput(){
        // check error on input field
        boolean isError = false;
        if (isEmpty(edtTanggalBook)){
            setErrorMessage(txInputTgl, "Date cannot be empty");
            isError = true;
        }else{
            txInputTgl.setErrorEnabled(false);
        }

        if (isEmpty(edtJam)){
            setErrorMessage(txInputJam, "Time cannot be empty");
            isError = true;
        }else{
            txInputJam.setErrorEnabled(false);
        }

        if (isEmpty(edtKendaraan)){
            setErrorMessage(txInputKend, "Vehicle type cannot be empty");
            isError = true;
        }else{
            txInputKend.setErrorEnabled(false);
        }

        if (isEmpty(edtParkir)){
            setErrorMessage(txInpurPark, "Parking type cannot be empty");
            isError = true;
        }else{
            txInpurPark.setErrorEnabled(false);
        }

        if (isEmpty(edtPlat)){
            setErrorMessage(txInputPlat, "Plat cannot be empty");
            isError = true;
        }else{
            txInputPlat.setErrorEnabled(false);
        }

        return isError;
    }

    private void dummyLoading(){
        // dummy loading
        final int interval = 1000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                hideProgressBar();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);
    }
}
