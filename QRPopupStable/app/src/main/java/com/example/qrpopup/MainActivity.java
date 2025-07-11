package com.example.qrpopup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.MultiFormatWriter;

public class MainActivity extends AppCompatActivity {

    private ImageView qrImageView;
    private Button toggleQRButton;
    private Button goToYouTubeButton;
    private boolean isQRVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrImageView = findViewById(R.id.qrImageView);
        toggleQRButton = findViewById(R.id.toggleQRButton);
        goToYouTubeButton = findViewById(R.id.goToYouTubeButton);

        toggleQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isQRVisible) {
                    // Ocultar
                    qrImageView.setVisibility(View.GONE);
                    goToYouTubeButton.setVisibility(View.GONE);
                    toggleQRButton.setText("Mostrar QR");
                    isQRVisible = false;
                } else {
                    // Mostrar
                    Bitmap qrBitmap = generateQR("https://www.youtube.com/");
                    qrImageView.setImageBitmap(qrBitmap);
                    qrImageView.setVisibility(View.VISIBLE);
                    goToYouTubeButton.setVisibility(View.VISIBLE);
                    toggleQRButton.setText("Ocultar QR");
                    isQRVisible = true;
                }
            }
        });

        goToYouTubeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=iuTtlb2COtc"));
                intent.setPackage("com.google.android.youtube"); // Lanza la app de YouTube si está
                startActivity(intent);
            }
        });
    }

    private Bitmap generateQR(String text) {
        int size = 512;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}