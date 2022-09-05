package com.example.barcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;


public class ScanQr extends AppCompatActivity {
    Button mscan_bn, mcopy_bn;
    TextView mscanned_text_tv;
    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        mscan_bn = findViewById(R.id.scan_bn);
        mcopy_bn = findViewById(R.id.copy_bn);
        mscanned_text_tv = findViewById(R.id.scanned_text_tv);


        mscan_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                new com.google.zxing.integration.android.IntentIntegrator(ScanQr.this).initiateScan();

                /*new com.google.zxing.integration.android.IntentIntegrator(ScanQr.this).setOrientationLocked(false);
                new com.google.zxing.integration.android.IntentIntegrator(ScanQr.this).setCaptureActivity(Capture.class);
                new com.google.zxing.integration.android.IntentIntegrator(ScanQr.this).setBeepEnabled(true);
                new com.google.zxing.integration.android.IntentIntegrator(ScanQr.this).setPrompt("Scanning");*/

                //IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQr.this);
                /*intentIntegrator.setPrompt("for flash, use value up");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();*/
            }
        });

        mcopy_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==1) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", mscanned_text_tv.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ScanQr.this, "Copied!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mscanned_text_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag==1) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("EditText", mscanned_text_tv.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ScanQr.this, "Copied!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult.getContents() != null) {
            mscanned_text_tv.setText(intentResult.getContents());
        } else {
            Toast.makeText(this, "something went to wong", Toast.LENGTH_SHORT).show();
        }
    }



}