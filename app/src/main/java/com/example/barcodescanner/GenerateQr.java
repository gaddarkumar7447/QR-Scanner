package com.example.barcodescanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
        getWindow().setStatusBarColor(ContextCompat.getColor(GenerateQr.this,R.color.black));
        minput_et = findViewById(R.id.input_et);
        mgenerate_bn = findViewById(R.id.generate_bn);
        moutput_iv = findViewById(R.id.output_iv);
        msave_gallery_bn = findViewById(R.id.save_gallery_bn);

        mgenerate_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = minput_et.getText().toString().trim();
                if (str.isEmpty()){
                    minput_et.setError("Can't generate empty qr code");
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

        msave_gallery_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1){
                    BitmapDrawable drawable = (BitmapDrawable) moutput_iv.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "QR code", "share QR code");
                    Uri bitmapUri = Uri.parse(bitmapPath);

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(GenerateQr.this, "Generate image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /*msave_gallery_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(GenerateQr.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    if(flag==1) {
                        saveImage();
                       // Toast.makeText(GenerateQr.this, "saved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    askPermission();
                }
            }
        });*/

    }
    /*private void askPermission() {
        ActivityCompat.requestPermissions(GenerateQr.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (flag == 1){
                    saveImage();
                }
            }
            else {
                Toast.makeText(this, "Please provide the request permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }*/
    /*public void saveImage(){
        Toast.makeText(this, "Image is saved", Toast.LENGTH_SHORT).show();
    }*/

}