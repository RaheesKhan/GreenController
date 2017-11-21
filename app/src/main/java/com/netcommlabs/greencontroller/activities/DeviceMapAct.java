package com.netcommlabs.greencontroller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netcommlabs.greencontroller.R;

public class DeviceMapAct extends AppCompatActivity {

    private TextView tvDvcsOneEvent;
    private DeviceMapAct mContext;
    private LinearLayout llScrnHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_map_act);

        initBase();

        initListeners();
    }

    private void initBase() {
        mContext = this;

        llScrnHeader = (LinearLayout) findViewById(R.id.llScrnHeader);
        tvDvcsOneEvent = (TextView) findViewById(R.id.tvDvcsOneEvent);
    }

    private void initListeners() {
        llScrnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.finish();
            }
        });

        tvDvcsOneEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddWtrngProfile = new Intent(mContext, DeviceDetails.class);
                mContext.startActivity(intentAddWtrngProfile);
            }
        });
    }
}
