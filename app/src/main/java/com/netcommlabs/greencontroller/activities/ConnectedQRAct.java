package com.netcommlabs.greencontroller.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcommlabs.greencontroller.R;

public class ConnectedQRAct extends AppCompatActivity {

    private ConnectedQRAct mContext;
    private LinearLayout llScrnHeader, llDeviceEditConncted, llAddDeviceAddressConctd;
    private TextView tvScanQREvent, tvNextConctdEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conted_qr_act);

        initBase();

        initListeners();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        mContext = this;
    }

    private void initBase() {
        mContext = this;

        llScrnHeader = (LinearLayout) findViewById(R.id.llScrnHeader);
        llDeviceEditConncted = (LinearLayout) findViewById(R.id.llDeviceEditConncted);
        llAddDeviceAddressConctd = (LinearLayout) findViewById(R.id.llAddDeviceAddressConctd);

        tvScanQREvent = (TextView) findViewById(R.id.tvScanQREvent);
        tvNextConctdEvent = (TextView) findViewById(R.id.tvNextConctdEvent);

    }

    private void initListeners() {
        llScrnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddWtrngProfile = new Intent(mContext, AvailableDevices.class);
                mContext.startActivity(intentAddWtrngProfile);
                mContext.finish();
            }
        });

        llDeviceEditConncted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Device connected", Toast.LENGTH_SHORT).show();
            }
        });

        llAddDeviceAddressConctd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddWtrngProfile = new Intent(mContext, AddAddressActivity.class);
                mContext.startActivity(intentAddWtrngProfile);
                mContext.finish();
            }
        });

        tvScanQREvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, QRScanAct.class);
                startActivityForResult(intent, 2);

                //zxingQRInitiateCamera();
            }
        });

        tvNextConctdEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddWtrngProfile = new Intent(mContext, EditSessionPlan.class);
                mContext.startActivity(intentAddWtrngProfile);
            }
        });

    }

    public void qrCodeDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConnectedQRAct.this);
        builder.setTitle("QR Result my custom");
        builder.setMessage(text);
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (data != null) {
                String message = data.getStringExtra("MESSAGE");
                qrCodeDialog(message);
            }
        }
    }

}
