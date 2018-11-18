package com.volunteam.mobilebijb.LayananBagasi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.volunteam.mobilebijb.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LayananBagasiDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_detail_bagasi)
    Toolbar toolbarDetailBagasi;
    @BindView(R.id.img_barcode_bike)
    ImageView imgBarcodeBike;
    @BindView(R.id.txt_kode_bagasi)
    TextView txtKodeBagasi;
    @BindView(R.id.bagasi_jemput)
    LinearLayout bagasiJemput;
    @BindView(R.id.bagasi_perjalanan_progress)
    View bagasiPerjalananProgress;
    @BindView(R.id.bagasi_perjalanan)
    LinearLayout bagasiPerjalanan;
    @BindView(R.id.bagasi_maskapai_progress)
    View bagasiMaskapaiProgress;
    @BindView(R.id.bagasi_maskapai)
    LinearLayout bagasiMaskapai;
    @BindView(R.id.bagasi_makeup_progress)
    View bagasiMakeupProgress;
    @BindView(R.id.bagasi_makeup)
    LinearLayout bagasiMakeup;
    @BindView(R.id.bagasi_plane_progress)
    View bagasiPlaneProgress;
    @BindView(R.id.bagasi_plane)
    LinearLayout bagasiPlane;

    String kodeBagasi;
    public final static int QRcodeWidth = 300;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layanan_bagasi_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarDetailBagasi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetailBagasi.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        kodeBagasi = getIntent().getStringExtra("kode");
        txtKodeBagasi.setText(kodeBagasi);
        startEncode();

    }

    public void startEncode() {
        try {
            bitmap = TextToImageEncode(kodeBagasi);

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
