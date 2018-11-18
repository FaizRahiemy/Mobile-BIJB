package com.volunteam.mobilebijb.AirportBike;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.volunteam.mobilebijb.AirportBike.pojo.GetBikeResponse;
import com.volunteam.mobilebijb.R;
import com.volunteam.mobilebijb.config.api.API;
import com.volunteam.mobilebijb.config.api.MainAPIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirportBikeDetailActivity extends AppCompatActivity {

    @BindView(R.id.img_barcode_bike)
    ImageView imgBarcodeBike;
    @BindView(R.id.txt_detail_bike)
    TextView txtDetailBike;
    @BindView(R.id.button_sewa_bike)
    Button buttonSewaBike;
    @BindView(R.id.toolbar_detail_bike)
    Toolbar toolbarDetailBike;

    String idBike;
    public final static int QRcodeWidth = 300;
    Bitmap bitmap;
    API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airport_bike_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarDetailBike);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetailBike.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        api = MainAPIHelper.getClient("").create(API.class);
        idBike = getIntent().getStringExtra("idBike");

        txtDetailBike.setText(Html.fromHtml("Kode sepeda: <b>" + idBike + "</b><br>Biaya sewa per 15 menit: <b>Rp. 1.000</b>"));
        startEncode();
    }

    @OnClick(R.id.button_sewa_bike)
    public void onViewClicked() {
        new AlertDialog.Builder(this)
            .setTitle("Sewa Sepeda")
            .setMessage("Yakin akan menyewa sepeda seharga Rp. 1.000?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    api.getBike(MainAPIHelper.key, idBike).enqueue(new Callback<GetBikeResponse>() {
                        @Override
                        public void onResponse(Call<GetBikeResponse> call, Response<GetBikeResponse> response) {
                            Toast.makeText(getApplicationContext(), response.body().getStatus(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<GetBikeResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Periksa koneksi internet anda!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }})
            .setNegativeButton(android.R.string.no, null).show();
    }

    public void startEncode() {
        try {
            bitmap = TextToImageEncode(idBike);

            imgBarcodeBike.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 300, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
