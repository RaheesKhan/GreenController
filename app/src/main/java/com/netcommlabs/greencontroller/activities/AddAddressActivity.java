package com.netcommlabs.greencontroller.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.netcommlabs.greencontroller.R;

public class AddAddressActivity extends AppCompatActivity {

    private AddAddressActivity mContext;
    private Button btnPlaceACTMap;
    private RadioGroup raGrAddressType;
    private RadioButton raBtnHome, raBtnOffice, raBtnOther;
    private EditText etOtherAddName;
    private LinearLayout llAddAddressName;
    private TextView tvSaveEvent;
    private LinearLayout llScrnHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_activity);

        initBase();

        initListeners();

        /*btnPlaceACTMap=(Button)findViewById(R.id.btnPlaceACTMap);
        btnPlaceACTMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddAddressActivity.this,PlaceACTandMap.class));

            }
        });*/
    }

    private void initBase() {
        mContext = this;

        llScrnHeader = (LinearLayout) findViewById(R.id.llScrnHeader);
        raGrAddressType = (RadioGroup) findViewById(R.id.raGrAddressType);
        etOtherAddName = (EditText) findViewById(R.id.etOtherAddName);
        llAddAddressName = (LinearLayout) findViewById(R.id.llAddAddressName);
        tvSaveEvent = (TextView) findViewById(R.id.tvSaveEvent);


    }

    private void initListeners() {
        llScrnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddWtrngProfile = new Intent(mContext, ConnectedQRAct.class);
                mContext.startActivity(intentAddWtrngProfile);
                mContext.finish();
            }
        });

        raGrAddressType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.raBtnHome) {
                    etOtherAddName.setVisibility(View.GONE);
                    setLLAddAddressHeightNumeric();
                    Toast.makeText(mContext, "Home", Toast.LENGTH_SHORT).show();
                } else if (checkedId == R.id.raBtnOffice) {
                    etOtherAddName.setVisibility(View.GONE);
                    setLLAddAddressHeightNumeric();
                    Toast.makeText(mContext, "Office", Toast.LENGTH_SHORT).show();
                } else {
                    etOtherAddName.setVisibility(View.VISIBLE);
                    setLLAddAddressHeightWrapContent();
                }
            }

            private void setLLAddAddressHeightWrapContent() {
                ViewGroup.LayoutParams layoutParams = llAddAddressName.getLayoutParams();
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                llAddAddressName.setLayoutParams(layoutParams);
            }

            private void setLLAddAddressHeightNumeric() {
                ViewGroup.LayoutParams layoutParams = llAddAddressName.getLayoutParams();
                layoutParams.height = 251;
                llAddAddressName.setLayoutParams(layoutParams);
            }
        });

        tvSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
                Toast.makeText(mContext, "Kindly click NEXT button now", Toast.LENGTH_SHORT).show();

            }
        });

    }

}
