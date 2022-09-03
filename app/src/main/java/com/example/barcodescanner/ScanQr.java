package com.example.barcodescanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
                IntentIntegrator intentIntegrator = new IntentIntegrator(ScanQr.this);
                intentIntegrator.setPrompt("for flash use valume up");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
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
            Toast.makeText(this, "OOPS....You didn't scan anything", Toast.LENGTH_SHORT).show();
        }
    }
}