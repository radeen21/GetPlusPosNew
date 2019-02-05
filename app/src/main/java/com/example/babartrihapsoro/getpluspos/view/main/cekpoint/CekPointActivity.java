package com.example.babartrihapsoro.getpluspos.view.main.cekpoint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.babartrihapsoro.getpluspos.R;
import com.example.babartrihapsoro.getpluspos.base.BaseActivity;
import com.example.babartrihapsoro.getpluspos.helper.ManifestPermission;
import com.example.babartrihapsoro.getpluspos.helper.Permission;
import com.example.babartrihapsoro.getpluspos.view.main.QrScannerView;
import com.example.babartrihapsoro.getpluspos.view.main.cekpoint.popupcekpoint.PopUpCekPoint;
import com.google.zxing.Result;

import butterknife.BindView;

public class CekPointActivity extends BaseActivity implements QrScannerView.ResultHandler {

    public static final String KEY_IS_FROM_LABEL_INPUT_QR = "key_is_from_label_input_qr";

    @BindView(R.id.fl_qr_scanner)
    FrameLayout flQrScanner;

    private QrScannerView scannerView;
    private boolean isFromLabelInputQr;
    private Permission cameraPermission;
    boolean isIquiry = false;

    int intActiveMenu = 0;


    @Override
    public int getLayout() {
        return R.layout.activity_qr;
    }

    @Override
    public void setup() {
        getExtras();
        isIquiry = false;
        checkCameraPermission();
    }

    private void getExtras() {
        isFromLabelInputQr = getIntent().getBooleanExtra(KEY_IS_FROM_LABEL_INPUT_QR,
                false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        destroyCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
        isIquiry = false;
    }

    private void checkCameraPermission() {
        cameraPermission = new Permission.Builder(this)
                .permission(ManifestPermission.CAMERA)
                .listener(new Permission.PermissionListener() {
                    @Override
                    public void onPermissionGranted(Permission.PermissionReport report) {
                        initScannerView();
                        bindViewToPresenter();
                        startCamera();
                    }

                    @Override
                    public void onPermissionDenied(Permission.PermissionReport report) {
                        finish();
                    }
                })
                .build();
        cameraPermission.check();
    }

    private void initScannerView() {
        scannerView = new QrScannerView(this);
        flQrScanner.addView(scannerView);
    }

    private void bindViewToPresenter() {
        Toast.makeText(this, "masuk Barcode", Toast.LENGTH_SHORT).show();
    }

    private void startCamera() {
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    public void destroyCamera() {
        // first permission check, there's possibility scannerView not initialized yet
        if (scannerView == null) return;

//        stopFlash();
        scannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        if (isFromLabelInputQr) {
            Toast.makeText(this, "masuk Barcode", Toast.LENGTH_SHORT).show();
//            goToPaymentForm();
        }
        super.onBackPressed();
    }


    @Override
    public void handleResult(Result rawResult) {
        Intent BackIntent = null;

        if(intActiveMenu == 0)
            BackIntent = new Intent(this, PopUpCekPoint.class);
        startActivity(BackIntent);
        finish();

//        Toast.makeText(this, rawResult.toString(), Toast.LENGTH_SHORT).show();
//        isIquiry = true;
//        scannerView.stopCameraPreview();
//        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (!cameraPermission.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
