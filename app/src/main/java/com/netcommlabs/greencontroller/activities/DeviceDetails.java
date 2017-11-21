package com.netcommlabs.greencontroller.activities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcommlabs.greencontroller.InterfaceValveAdapter;
import com.netcommlabs.greencontroller.R;
import com.netcommlabs.greencontroller.adapters.ValvesListAdapter;

import java.util.ArrayList;

public class DeviceDetails extends AppCompatActivity {

    private RecyclerView reviValvesList;
    private DeviceDetails mContext;
    private ArrayList<String> listValves;
    private LinearLayout llControllerNameEdit, llControllerNameSave, llStartStopCNT, llReconnectCNT, llPauseCNT, llDeleteCNT, llPauseValve, llFlushValve;
    private EditText etContrlrName;
    private TextView tvCntrlrName;/* tvEditProfileEvent, tvChooseProfileEvent, tvValveNameAct, tvStartStop*/
    ;
    private ImageView ivSaveCntrlrName, ivEditPen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_details);

        initBase();

        initListeners();
    }

    private void initBase() {
        mContext = this;

        //tvStartStop=(TextView)findViewById(R.id.tvStartStop);
      /*  tvValveNameAct = (TextView) findViewById(R.id.tvValveNameAct);
        tvEditProfileEvent = (TextView) findViewById(R.id.tvEditProfileEvent);
        tvChooseProfileEvent = (TextView) findViewById(R.id.tvChooseProfileEvent);*/
        tvCntrlrName = (TextView) findViewById(R.id.tvCntrlrName);
       /* ivSaveCntrlrName = (ImageView) findViewById(R.id.ivSaveCntrlrName);
        ivEditPen = (ImageView) findViewById(R.id.ivEditPen);
        etContrlrName = (EditText) findViewById(R.id.etContrlrName);
        llControllerNameEdit = (LinearLayout) findViewById(R.id.llControllerNameEdit);
        llControllerNameSave = (LinearLayout) findViewById(R.id.llControllerNameSave);
        llStartStopCNT = (LinearLayout) findViewById(R.id.llStartStopCNT);
        llReconnectCNT = (LinearLayout) findViewById(R.id.llReconnectCNT);
        llPauseCNT = (LinearLayout) findViewById(R.id.llPauseCNT);
        llDeleteCNT = (LinearLayout) findViewById(R.id.llDeleteCNT);*/
        llPauseValve = (LinearLayout) findViewById(R.id.llPauseValve);
        llFlushValve = (LinearLayout) findViewById(R.id.llFlushValve);

        reviValvesList = (RecyclerView) findViewById(R.id.reviValvesList);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
        reviValvesList.setLayoutManager(gridLayoutManager);

        listValves = new ArrayList<>();

        listValves.add("Valve 1");
        listValves.add("Valve 2");
        listValves.add("Valve 3");
        listValves.add("Valve 4");
        listValves.add("Valve 5");
        listValves.add("Valve 6");
        listValves.add("Valve 7");
        listValves.add("Valve 8");

        reviValvesList.setAdapter(new ValvesListAdapter(mContext, listValves));
    }

    private void initListeners() {
      /*  llControllerNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ivEditPen.setBackgroundResource(R.drawable.circle_wo_shadow_light);

                llControllerNameEdit.setVisibility(View.GONE);
                llControllerNameSave.setVisibility(View.VISIBLE);

                etContrlrName.setText("");
                etContrlrName.requestFocus();


                Toast.makeText(mContext, "Enter Controller Name", Toast.LENGTH_SHORT).show();

            }
        });

        ivSaveCntrlrName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCNTName = etContrlrName.getText().toString();
                if (enteredCNTName.isEmpty()) {
                    Toast.makeText(mContext, "Controller name can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                llControllerNameEdit.setVisibility(View.VISIBLE);
                llControllerNameSave.setVisibility(View.GONE);
                tvCntrlrName.setText(enteredCNTName);
                Toast.makeText(mContext, "Name Edited Successfully", Toast.LENGTH_SHORT).show();
            }
        });

      *//*  llStartStopCNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStartStop=v.findViewById(R.id.tvStartStop);
                if (tvStartStop.getText().equals("Start")){
                    tvStartStop.setText("Stop");
                }else {
                    tvStartStop.setText("Start");
                }
            }
        });
*//*
        llReconnectCNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Reconnect", Toast.LENGTH_SHORT).show();
            }
        });

        llPauseCNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Pause", Toast.LENGTH_SHORT).show();
            }
        });

        llDeleteCNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
            }
        });
*/
        llPauseValve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Pause Valve", Toast.LENGTH_SHORT).show();
            }
        });

        llFlushValve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Flush Valve", Toast.LENGTH_SHORT).show();
            }
        });

      /*  tvEditProfileEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Edit Profile", Toast.LENGTH_SHORT).show();
            }
        });

        tvChooseProfileEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Choose Profile", Toast.LENGTH_SHORT).show();
            }
        });*/
    }

  /*  @Override
    public void onBackPressed() {
        if (llControllerNameSave.getVisibility() == View.VISIBLE) {
            llControllerNameEdit.setVisibility(View.VISIBLE);
            llControllerNameSave.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }*/

  /*  @Override
    public void clickPassDataToAct(String s) {
        tvValveNameAct.setText(s);
    }*/
}
