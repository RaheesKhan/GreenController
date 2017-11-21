package com.netcommlabs.greencontroller.activities;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.netcommlabs.greencontroller.R;
import com.netcommlabs.greencontroller.adapters.AdptrAvailableDVCs;

import java.util.ArrayList;
import java.util.List;

public class AvailableDevices extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private RecyclerView reViListAvailDvc;
    private AvailableDevices mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_CODE_ENABLE = 1;
    private ProgressBar progrsBarIndetmnt;
    List<BluetoothDevice> listAvailbleDvcs;
    private TextView tvScanAgainEvent;
    private LinearLayout llScrnHeader, llNoDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.available_devices);

        initBase();

        initListeners();

    }

    private void initBase() {
        mContext = this;

        tvScanAgainEvent = (TextView) findViewById(R.id.tvScanAgainEvent);
        llNoDevice = (LinearLayout) findViewById(R.id.llNoDevice);
        llScrnHeader = (LinearLayout) findViewById(R.id.llScrnHeader);
        progrsBarIndetmnt = (ProgressBar) findViewById(R.id.progrsBarIndetmnt);

        reViListAvailDvc = (RecyclerView) findViewById(R.id.reViListAvailDvc);
        LinearLayoutManager llManagerAailDvcs = new LinearLayoutManager(this);
        reViListAvailDvc.setLayoutManager(llManagerAailDvcs);

        listAvailbleDvcs = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startBTWork();
        } else {
            ActivityCompat.requestPermissions(mContext,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }


    }

    private void startBTWork() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(mContext, "Device don't support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBluetoothAdapter.isEnabled()) {
                startDvcDiscovery();
                return;
            }
            Intent intentBTEnableRqst = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intentBTEnableRqst, REQUEST_CODE_ENABLE);
        }
    }

    private void initListeners() {
        llScrnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAvailableDbc = new Intent(mContext, DontHaveDvc.class);
                startActivity(intentAvailableDbc);
                mContext.finish();
            }
        });

        tvScanAgainEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDvcDiscovery();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //------ If bluetooth is enabled-------
        if (requestCode == REQUEST_CODE_ENABLE && resultCode == Activity.RESULT_OK) {
            startDvcDiscovery();
        } else {
            Toast.makeText(this, "Bluetooth enabling is mandatory", Toast.LENGTH_SHORT).show();
            Intent intentAddWtrngProfile = new Intent(mContext, DontHaveDvc.class);
            mContext.startActivity(intentAddWtrngProfile);
            finish();
        }
    }

    private void startDvcDiscovery() {
        if (mBluetoothAdapter.isEnabled()) {

            IntentFilter intentFilterActnFound = new IntentFilter();
            intentFilterActnFound.addAction(BluetoothDevice.ACTION_FOUND);
            intentFilterActnFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
            intentFilterActnFound.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            mContext.registerReceiver(mBroadcastReceiver, intentFilterActnFound);

            mBluetoothAdapter.startDiscovery();

        } else {
            Toast.makeText(mContext, "Kindly turn BT ON", Toast.LENGTH_SHORT).show();
        }
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                progrsBarIndetmnt.setVisibility(View.VISIBLE);
                listAvailbleDvcs.clear();

                reViListAvailDvc.setVisibility(View.VISIBLE);
                llNoDevice.setVisibility(View.GONE);
            }

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {

                //Toast.makeText(mContext, "Device Found", Toast.LENGTH_SHORT).show();
                BluetoothDevice availbleDvc = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (!listAvailbleDvcs.contains(availbleDvc)) {
                    listAvailbleDvcs.add(availbleDvc);

                    reViListAvailDvc.setAdapter(new AdptrAvailableDVCs(mContext, listAvailbleDvcs, mBluetoothAdapter));
                }

            }

            if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                progrsBarIndetmnt.setVisibility(View.GONE);

                if (listAvailbleDvcs.size() < 1) {
                    reViListAvailDvc.setVisibility(View.GONE);
                    llNoDevice.setVisibility(View.VISIBLE);
                }

                //mBluetoothAdapter.cancelDiscovery();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            Log.e("!!!!!BCR ", e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startBTWork();

                } else {
                    Toast.makeText(mContext, "Location permission is required...", Toast.LENGTH_SHORT).show();

                    Intent intentAddWtrngProfile = new Intent(mContext, DontHaveDvc.class);
                    mContext.startActivity(intentAddWtrngProfile);

                    mContext.finish();
                }
                return;
            }

        }
    }
}
