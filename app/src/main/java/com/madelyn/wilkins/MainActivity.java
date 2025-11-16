package com.madelyn.wilkins;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;

import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;
import com.madelyn.wilkins.R;
import com.madelyn.wilkins.Tables;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;
    private BarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barcodeView = findViewById(R.id.barcode_scanner);

        // Only scan QR codes
        barcodeView.setDecoderFactory(
                new DefaultDecoderFactory(Arrays.asList(com.google.zxing.BarcodeFormat.QR_CODE))
        );

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            startScanning();
        }
    }

    private void startScanning() {
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() != null) {
                    barcodeView.pause(); // Stop scanning after first QR

                    String scannedData = result.getText();
                    showNameDialog(scannedData);
                }
            }

            @Override
            public void possibleResultPoints(java.util.List<com.google.zxing.ResultPoint> resultPoints) { }
        });
        barcodeView.resume();
    }

    private void showNameDialog(String scannedData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_name_input, null);

        EditText etNameDialog = dialogView.findViewById(R.id.etNameDialog);

        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String userName = etNameDialog.getText().toString().trim();

            if (!userName.isEmpty()) {
                // Go to tables activity and pass scannedData and userName
                Intent intent = new Intent(MainActivity.this, Tables.class);
                intent.putExtra("qr_result", scannedData);
                intent.putExtra("user_name", userName);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
                barcodeView.resume(); // resume scanning if empty
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            barcodeView.resume(); // resume scanning
        });

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning();
            } else {
                Toast.makeText(this, "Camera permission is required to scan QR codes", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}
