package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQr extends AppCompatActivity {
    EditText minput_et;
    TextView mgenerate_bn;
    ImageView moutput_iv;
    Button msave_gallery_bn;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        minput_et = findViewById(R.id.input_et);
        mgenerate_bn = findViewById(R.id.generate_bn);
        moutput_iv = findViewById(R.id.output_iv);
        msave_gallery_bn = findViewById(R.id.save_gallery_bn);


        mgenerate_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = minput_et.getText().toString().trim();
                if (str.isEmpty()){
                    minput_et.setError("Con't generate empty qr code");
                    //Toast.makeText(GenerateQr.this, "Con't generate empty qr code", Toast.LENGTH_SHORT).show();
                }
                else{
                    flag = 1;
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = writer.encode(str, BarcodeFormat.QR_CODE,350,350);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bit = barcodeEncoder.createBitmap(matrix);
                        moutput_iv.setImageBitmap(bit);
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(minput_et.getApplicationWindowToken(), 0);
                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}