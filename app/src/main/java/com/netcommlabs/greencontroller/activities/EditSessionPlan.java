package com.netcommlabs.greencontroller.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothGattService;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.netcommlabs.greencontroller.Constants;
import com.netcommlabs.greencontroller.R;
import com.netcommlabs.greencontroller.services.BleAdapterService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class EditSessionPlan extends AppCompatActivity implements View.OnClickListener {

    private EditSessionPlan mContext;
    private LinearLayout llScrnHeader;
    private Calendar calendar;
    private EditText etDischargePoints, etDurationPlan, etQuantityPlan;
    private TextView tvSunEvent, tvMonEvent, tvTueEvent, tvWedEvent, tvThuEvent, tvFriEvent, tvSatEvent, tvSunFirst, tvSunSecond, tvSunThird, tvSunFourth, tvMonFirst, tvMonSecond, tvMonThird, tvMonFourth, tvTueFirst, tvTueSecond, tvTueThird, tvTueFourth, tvWedFirst, tvWedSecond, tvWedThird, tvWedFourth, tvThuFirst, tvThuSecond, tvThuThird, tvThuFourth, tvFriFirst, tvFriSecond, tvFriThird, tvFriFourth, tvSatFirst, tvSatSecond, tvSatThird, tvSatFourth, tvLoadSesnPlan;
    private ImageView ivSunAdd, ivMonAdd, ivTueAdd, ivWedAdd, ivThuAdd, ivFriAdd, ivSatAdd;
    private int timePointsCounter, sunTimePointsCount, monTimePointsCount, tueTimePointsCount, wedTimePointsCount, thuTimePointsCount, friTimePointsCount, satTimePointsCount, etDurWtrInputInt, etQuantWtrInputInt, etPotsInputInt, inputSunInt, inputMonInt, inputTueInt, inputWedInt, inputThuInt, inputFriInt, inputSatInt;
    private HashMap<Integer, List<Integer>> mapDayTimings;
    private View viewSelectedRound;
    private List<HashMap> globalLIst = null;

    private View viewDummy;
    String etGetInputTimePoints = "00:00";
    private ArrayList<Integer> listTimePntsSun, listTimePntsMon, listTimePntsTue, listTimePntsWed, listTimePntsThu, listTimePntsFri, listTimePntsSat;
    private int etGetInputTimePointsInt;
    private String etInputDischrgPnts, etInputDursnPlan, etQuantPlan;
    private Dialog dialogShowInfo;
    private EditText etInputTimePoint;

    //Mr. Vijay
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ID = "id";
    private BleAdapterService bluetooth_le_adapter;

    private String device_name;
    private String device_address;
    private boolean back_requested = false;
    private int alert_level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_session_plan);

        initBase();

        initListeners();
    }

    private void initBase() {
        mContext = this;

        mapDayTimings = new HashMap<>();

        listTimePntsSun = new ArrayList<Integer>();
        listTimePntsMon = new ArrayList<Integer>();
        listTimePntsTue = new ArrayList<Integer>();
        listTimePntsWed = new ArrayList<Integer>();
        listTimePntsThu = new ArrayList<Integer>();
        listTimePntsFri = new ArrayList<Integer>();
        listTimePntsSat = new ArrayList<Integer>();

        llScrnHeader = (LinearLayout) findViewById(R.id.llScrnHeader);

        etDischargePoints = (EditText) findViewById(R.id.etDischargePoints);
        etDurationPlan = (EditText) findViewById(R.id.etDurationPlan);
        etQuantityPlan = (EditText) findViewById(R.id.etQuantityPlan);

        tvSunEvent = (TextView) findViewById(R.id.tvSunEvent);
        tvMonEvent = (TextView) findViewById(R.id.tvMonEvent);
        tvTueEvent = (TextView) findViewById(R.id.tvTueEvent);
        tvWedEvent = (TextView) findViewById(R.id.tvWedEvent);
        tvThuEvent = (TextView) findViewById(R.id.tvThuEvent);
        tvFriEvent = (TextView) findViewById(R.id.tvFriEvent);
        tvSatEvent = (TextView) findViewById(R.id.tvSatEvent);

        tvSunFirst = (TextView) findViewById(R.id.tvSunFirst);
        tvSunSecond = (TextView) findViewById(R.id.tvSunSecond);
        tvSunThird = (TextView) findViewById(R.id.tvSunThird);
        tvSunFourth = (TextView) findViewById(R.id.tvSunFourth);
        ivSunAdd = (ImageView) findViewById(R.id.ivSunAdd);

        tvMonFirst = (TextView) findViewById(R.id.tvMonFirst);
        tvMonSecond = (TextView) findViewById(R.id.tvMonSecond);
        tvMonThird = (TextView) findViewById(R.id.tvMonThird);
        tvMonFourth = (TextView) findViewById(R.id.tvMonFourth);
        ivMonAdd = (ImageView) findViewById(R.id.ivMonAdd);

        tvTueFirst = (TextView) findViewById(R.id.tvTueFirst);
        tvTueSecond = (TextView) findViewById(R.id.tvTueSecond);
        tvTueThird = (TextView) findViewById(R.id.tvTueThird);
        tvTueFourth = (TextView) findViewById(R.id.tvTueFourth);
        ivTueAdd = (ImageView) findViewById(R.id.ivTueAdd);

        tvWedFirst = (TextView) findViewById(R.id.tvWedFirst);
        tvWedSecond = (TextView) findViewById(R.id.tvWedSecond);
        tvWedThird = (TextView) findViewById(R.id.tvWedThird);
        tvWedFourth = (TextView) findViewById(R.id.tvWedFourth);
        ivWedAdd = (ImageView) findViewById(R.id.ivWedAdd);

        tvThuFirst = (TextView) findViewById(R.id.tvThuFirst);
        tvThuSecond = (TextView) findViewById(R.id.tvThuSecond);
        tvThuThird = (TextView) findViewById(R.id.tvThuThird);
        tvThuFourth = (TextView) findViewById(R.id.tvThuFourth);
        ivThuAdd = (ImageView) findViewById(R.id.ivThuAdd);

        tvFriFirst = (TextView) findViewById(R.id.tvFriFirst);
        tvFriSecond = (TextView) findViewById(R.id.tvFriSecond);
        tvFriThird = (TextView) findViewById(R.id.tvFriThird);
        tvFriFourth = (TextView) findViewById(R.id.tvFriFourth);
        ivFriAdd = (ImageView) findViewById(R.id.ivFriAdd);

        tvSatFirst = (TextView) findViewById(R.id.tvSatFirst);
        tvSatSecond = (TextView) findViewById(R.id.tvSatSecond);
        tvSatThird = (TextView) findViewById(R.id.tvSatThird);
        tvSatFourth = (TextView) findViewById(R.id.tvSatFourth);
        ivSatAdd = (ImageView) findViewById(R.id.ivSatAdd);

        tvLoadSesnPlan = (TextView) findViewById(R.id.tvLoadSesnPlan);

        //Mr. Vijay
        //read intent data
        final Intent intent = getIntent();
        device_name = intent.getStringExtra(EXTRA_NAME);
        device_address = intent.getStringExtra(EXTRA_ID);


        // connect to the Bluetooth adapter service
        Intent gattServiceIntent = new Intent(this, BleAdapterService.class);
        bindService(gattServiceIntent, service_connection, BIND_AUTO_CREATE);
        showMsg("READY");

        //onConnect(viewDummy);

    }

    private void initListeners() {

        llScrnHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        etDischargePoints.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                visibleCursorSoftKeyboard();
                return false;
            }
        });

        etDurationPlan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                visibleCursorSoftKeyboard();
                return false;
            }
        });

        etQuantityPlan.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                visibleCursorSoftKeyboard();
                return false;
            }
        });

        tvSunEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivSunAdd.setVisibility(View.GONE);
                }

            }
        });

        tvMonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivMonAdd.setVisibility(View.GONE);
                }
            }
        });

        tvTueEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivTueAdd.setVisibility(View.GONE);
                }
            }
        });

        tvWedEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivWedAdd.setVisibility(View.GONE);
                }
            }
        });

        tvThuEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivThuAdd.setVisibility(View.GONE);
                }
            }
        });

        tvFriEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();

                } else {
                    viewSelectedRound.setSelected(false);
                    ivFriAdd.setVisibility(View.GONE);
                }
            }
        });

        tvSatEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSelectedRound = v;
                if (!viewSelectedRound.isSelected()) {
                    setViewSelectedRound();
                } else {
                    viewSelectedRound.setSelected(false);
                    ivSatAdd.setVisibility(View.GONE);
                }
            }
        });

        tvSunFirst.setOnClickListener(this);
        tvSunSecond.setOnClickListener(this);
        tvSunThird.setOnClickListener(this);
        tvSunFourth.setOnClickListener(this);
        tvMonFirst.setOnClickListener(this);
        tvMonSecond.setOnClickListener(this);
        tvMonThird.setOnClickListener(this);
        tvMonFourth.setOnClickListener(this);
        tvTueFirst.setOnClickListener(this);
        tvTueSecond.setOnClickListener(this);
        tvTueThird.setOnClickListener(this);
        tvTueFourth.setOnClickListener(this);
        tvWedFirst.setOnClickListener(this);
        tvWedSecond.setOnClickListener(this);
        tvWedThird.setOnClickListener(this);
        tvWedFourth.setOnClickListener(this);
        tvThuFirst.setOnClickListener(this);
        tvThuSecond.setOnClickListener(this);
        tvThuThird.setOnClickListener(this);
        tvThuFourth.setOnClickListener(this);
        tvFriFirst.setOnClickListener(this);
        tvFriSecond.setOnClickListener(this);
        tvFriThird.setOnClickListener(this);
        tvFriFourth.setOnClickListener(this);
        tvSatFirst.setOnClickListener(this);
        tvSatSecond.setOnClickListener(this);
        tvSatThird.setOnClickListener(this);
        tvSatFourth.setOnClickListener(this);

        ivSunAdd.setOnClickListener(this);
        ivMonAdd.setOnClickListener(this);
        ivTueAdd.setOnClickListener(this);
        ivWedAdd.setOnClickListener(this);
        ivThuAdd.setOnClickListener(this);
        ivFriAdd.setOnClickListener(this);
        ivSatAdd.setOnClickListener(this);


        tvLoadSesnPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalLIst = new ArrayList<>();
                etInputDischrgPnts = etDischargePoints.getText().toString();
                etInputDursnPlan = etDurationPlan.getText().toString();
                etQuantPlan = etQuantityPlan.getText().toString();

               /* if (etInputDischrgPnts.isEmpty()) {
                    Toast.makeText(mContext, "Please enter Discharge points", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etInputDursnPlan.isEmpty()) {
                    Toast.makeText(mContext, "Please enter Duration", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (etQuantPlan.isEmpty()) {
                    Toast.makeText(mContext, "Please enter Quantity", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (inputSunInt == 0 && inputMonInt == 0 && inputTueInt == 0 && inputWedInt == 0 && inputThuInt == 0 && inputFriInt == 0 && inputSunInt == 0) {
                    Toast.makeText(mContext, "Please select at least one day in week", Toast.LENGTH_LONG).show();
                    return;
                }

                int etInputDischrgPntsInt = Integer.parseInt(etInputDischrgPnts);
                int etInputDursnPlanInt = Integer.parseInt(etInputDursnPlan);
                int etQuantPlanInt = Integer.parseInt(etQuantPlan);*/

                if (listTimePntsSun.size() > 0) {
                    mapDayTimings.put(1, listTimePntsSun);
                }
                if (listTimePntsMon.size() > 0) {
                    mapDayTimings.put(2, listTimePntsMon);
                }
                if (listTimePntsTue.size() > 0) {
                    mapDayTimings.put(3, listTimePntsTue);
                }
                if (listTimePntsWed.size() > 0) {
                    mapDayTimings.put(4, listTimePntsWed);
                }
                if (listTimePntsThu.size() > 0) {
                    mapDayTimings.put(5, listTimePntsThu);
                }
                if (listTimePntsFri.size() > 0) {
                    mapDayTimings.put(6, listTimePntsFri);
                }
                if (listTimePntsSat.size() > 0) {
                    mapDayTimings.put(7, listTimePntsSat);
                }


                Toast.makeText(mContext, "Load check", Toast.LENGTH_SHORT).show();

                /*if (mapDayTimings.size() > 0) {

                    for (int key : mapDayTimings.keySet()) {
                        switch (key) {
                            case 1:
                                for (int i = 0; i < listTimePntsSun.size(); i++) {
                                    onSendTPDynamic(listTimePntsSun.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 2:
                                for (int i = 0; i < listTimePntsMon.size(); i++) {
                                    onSendTPDynamic(listTimePntsMon.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 3:
                                for (int i = 0; i < listTimePntsTue.size(); i++) {
                                    onSendTPDynamic(listTimePntsTue.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 4:
                                for (int i = 0; i < listTimePntsWed.size(); i++) {
                                    onSendTPDynamic(listTimePntsWed.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 5:
                                for (int i = 0; i < listTimePntsThu.size(); i++) {
                                    onSendTPDynamic(listTimePntsThu.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 6:
                                for (int i = 0; i < listTimePntsFri.size(); i++) {
                                    onSendTPDynamic(listTimePntsFri.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;
                            case 7:
                                for (int i = 0; i < listTimePntsSat.size(); i++) {
                                    onSendTPDynamic(listTimePntsSat.get(i), 0, 0, key, 1, 5, 2, 0);
                                }
                                break;

                        }

                    }

                }*/

            }
        });
    }

//Start Mr. Vijay

    private final ServiceConnection service_connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            bluetooth_le_adapter = ((BleAdapterService.LocalBinder) service).getService();
            bluetooth_le_adapter.setActivityHandler(message_handler);

            showMsg("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bluetooth_le_adapter = null;

            showMsg("onServiceDisconnected");
        }
    };

    private Handler message_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle;
            String service_uuid = "";
            String characteristic_uuid = "";
            byte[] b = null;
            //message handling logic
            switch (msg.what) {
                case BleAdapterService.MESSAGE:
                    bundle = msg.getData();
                    String text = bundle.getString(BleAdapterService.PARCEL_TEXT);
                    showMsg(text);
                    break;

                case BleAdapterService.GATT_CONNECTED:
                    //((Button) PeripheralControlActivity.this.findViewById(R.id.connectButton)).setEnabled(false);
                    //we're connected
                    showMsg("GATT_CONNECTED");
                    // enable the LOW/MID/HIGH alert level selection buttons
                    //((Button) PeripheralControlActivity.this.findViewById(R.id.lowButton)).setEnabled(true);
                    //((Button) PeripheralControlActivity.this.findViewById(R.id.midButton)).setEnabled(true);
                    //((Button) PeripheralControlActivity.this.findViewById(R.id.highButton)).setEnabled(true);
                    bluetooth_le_adapter.discoverServices();
                    break;

                case BleAdapterService.GATT_DISCONNECT:
                    //((Button) PeripheralControlActivity.this.findViewById(R.id.connectButton)).setEnabled(true);
                    //we're disconnected
                    showMsg("GATT_DISCONNECT");
                    // hide the rssi distance colored rectangle
                    /*((LinearLayout) PeripheralControlActivity.this.findViewById(R.id.rectangle)).setVisibility(View.INVISIBLE);
                    // disable the LOW/MID/HIGH alert level selection buttons
                    ((Button) PeripheralControlActivity.this.findViewById(R.id.lowButton)).setEnabled(false);
                    ((Button) PeripheralControlActivity.this.findViewById(R.id.midButton)).setEnabled(false);
                    ((Button) PeripheralControlActivity.this.findViewById(R.id.highButton)).setEnabled(false);*/
                    if (back_requested) {
                        mContext.finish();
                    }
                    break;

                case BleAdapterService.GATT_SERVICES_DISCOVERED:
                    showMsg("GATT_SERVICES_DISCOVERED");
                    //validate services and if ok...
                    List<BluetoothGattService> slist = bluetooth_le_adapter.getSupportedGattServices();
                    boolean time_point_service_present = false;
                    boolean current_time_service_present = false;
                    boolean pots_service_present = false;
                    boolean battery_service_present = false;
                    boolean valve_controller_service_present = false;

                    for (BluetoothGattService svc : slist) {
                        Log.d(Constants.TAG, "UUID=" + svc.getUuid().toString().toUpperCase() + "INSTANCE=" + svc.getInstanceId());
                        String serviceUuid = svc.getUuid().toString().toUpperCase();
                        if (svc.getUuid().toString().equalsIgnoreCase(BleAdapterService.TIME_POINT_SERVICE_SERVICE_UUID)) {
                            time_point_service_present = true;
                            continue;
                        }
                        if (svc.getUuid().toString().equalsIgnoreCase(BleAdapterService.CURRENT_TIME_SERVICE_SERVICE_UUID)) {
                            current_time_service_present = true;
                            continue;
                        }
                        if (svc.getUuid().toString().equalsIgnoreCase(BleAdapterService.POTS_SERVICE_SERVICE_UUID)) {
                            pots_service_present = true;
                            continue;
                        }
                        if (svc.getUuid().toString().equalsIgnoreCase(BleAdapterService.BATTERY_SERVICE_SERVICE_UUID)) {
                            battery_service_present = true;
                            continue;
                        }
                        if (svc.getUuid().toString().equalsIgnoreCase(BleAdapterService.VALVE_CONTROLLER_SERVICE_UUID)) {
                            valve_controller_service_present = true;
                            continue;
                        }
                    }

                    if (time_point_service_present && current_time_service_present && pots_service_present && battery_service_present) {
                        showMsg("Device has expected services");


                        //enable the LOW/MID/HIGH alert level selection buttons
                        //((Button) PeripheralControlActivity.this.findViewById(R.id.lowButton)).setEnabled(true);
                        //((Button) PeripheralControlActivity.this.findViewById(R.id.midButton)).setEnabled(true);
                        //((Button) PeripheralControlActivity.this.findViewById(R.id.highButton)).setEnabled(true);
                       /* ((Button) PeripheralControlActivity.this.findViewById(R.id.potsButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.timeButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.timepointButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.flushButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.startButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.stopButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.batteryButton)).setEnabled(true);
                        ((Button) PeripheralControlActivity.this.findViewById(R.id.pauseButton)).setEnabled(true);*/

                    } else {
                        showMsg("Device does not have expected GATT services");
                    }
                    break;

                case BleAdapterService.GATT_CHARACTERISTIC_READ:
                    bundle = msg.getData();
                    Log.d(Constants.TAG, "Service=" + bundle.get(BleAdapterService.PARCEL_SERVICE_UUID).toString().toUpperCase() + " Characteristic=" + bundle.get(BleAdapterService.PARCEL_CHARACTERISTIC_UUID).toString().toUpperCase());
                    if (bundle.get(BleAdapterService.PARCEL_CHARACTERISTIC_UUID).toString()
                            .toUpperCase().equals(BleAdapterService.ALERT_LEVEL_CHARACTERISTIC)
                            && bundle.get(BleAdapterService.PARCEL_SERVICE_UUID).toString().toUpperCase().equals(BleAdapterService.BATTERY_LEVEL_CHARACTERISTIC_UUID)) {
                        b = bundle.getByteArray(BleAdapterService.PARCEL_VALUE);
                        if (b.length > 0) {
                            mContext.setAlertLevel((int) b[0]);
                            showMsg("Received " + b.toString() + "from Pebble.");
                        }
                    }

                    break;

                case BleAdapterService.GATT_CHARACTERISTIC_WRITTEN:
                    bundle = msg.getData();
                    if (bundle.get(BleAdapterService.PARCEL_CHARACTERISTIC_UUID).toString()
                            .toUpperCase().equals(BleAdapterService.ALERT_LEVEL_CHARACTERISTIC)
                            && bundle.get(BleAdapterService.PARCEL_SERVICE_UUID).toString().toUpperCase().equals(BleAdapterService.LINK_LOSS_SERVICE_UUID)) {
                        b = bundle.getByteArray(BleAdapterService.PARCEL_VALUE);
                        if (b.length > 0) {
                            mContext.setAlertLevel((int) b[0]);
                        }
                    }
                    break;
            }
        }
    };

    public void onLow(View view) {
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.LINK_LOSS_SERVICE_UUID,
                BleAdapterService.ALERT_LEVEL_CHARACTERISTIC, Constants.ALERT_LEVEL_LOW
        );
    }

    public void onMid(View view) {
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.LINK_LOSS_SERVICE_UUID,
                BleAdapterService.ALERT_LEVEL_CHARACTERISTIC, Constants.ALERT_LEVEL_MID
        );
    }

    public void onHigh(View view) {
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.LINK_LOSS_SERVICE_UUID,
                BleAdapterService.ALERT_LEVEL_CHARACTERISTIC, Constants.ALERT_LEVEL_HIGH
        );
    }

    public void onSetTime(View view) {
        String[] ids = TimeZone.getAvailableIDs(+5 * 60 * 60 * 1000);
        SimpleTimeZone pdt = new SimpleTimeZone(+5 * 60 * 60 * 1000, ids[0]);

        Calendar calendar = new GregorianCalendar(pdt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);

        //Set present time as data packet
        byte hours = (byte) calendar.get(Calendar.HOUR);
        if (calendar.get(Calendar.AM_PM) == 1) {
            hours = (byte) (calendar.get(Calendar.HOUR) + 12);
        }
        byte minutes = (byte) calendar.get(Calendar.MINUTE);
        byte seconds = (byte) calendar.get(Calendar.SECOND);
        byte DATE = (byte) calendar.get(Calendar.DAY_OF_MONTH);
        byte MONTH = (byte) (calendar.get(Calendar.MONTH) + 1);
        int iYEARMSB = (calendar.get(Calendar.YEAR) / 256);
        int iYEARLSB = (calendar.get(Calendar.YEAR) % 256);
        byte bYEARMSB = (byte) iYEARMSB;
        byte bYEARLSB = (byte) iYEARLSB;

        //Set 1,2,3,4,5,6,7 as data packet
        /*byte hours = (byte) 1;
        byte minutes = (byte) 2;
        byte seconds = (byte) 3;
        byte DATE = (byte) 4;
        byte MONTH = (byte) 5;
        //int iYEARMSB = (calendar.get(Calendar.YEAR) / 256);
        //int iYEARLSB = (calendar.get(Calendar.YEAR) % 256);
        //byte bYEARMSB = (byte) iYEARMSB;
        //byte bYEARLSB = (byte) iYEARLSB;
        byte bYEARMSB = (byte) 6;
        byte bYEARLSB = (byte) 7;*/

        byte[] currentTime = {hours, minutes, seconds, DATE, MONTH, bYEARMSB, bYEARLSB};

        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.CURRENT_TIME_SERVICE_SERVICE_UUID,
                BleAdapterService.CURRENT_TIME_CHARACTERISTIC_UUID, currentTime
        );
    }

    public void onSetPots(View view) {
        byte numberOfPots = (byte) 5;
        byte[] pots = {numberOfPots};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.POTS_SERVICE_SERVICE_UUID,
                BleAdapterService.POTS_CHARACTERISTIC_UUID, pots
        );
    }

    public void onFlush(View view) {
        byte[] valveCommand = {1};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.VALVE_CONTROLLER_SERVICE_UUID,
                BleAdapterService.COMMAND_CHARACTERISTIC_UUID, valveCommand
        );
    }

    public void onStart(View view) {
        byte[] valveCommand = {2};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.VALVE_CONTROLLER_SERVICE_UUID,
                BleAdapterService.COMMAND_CHARACTERISTIC_UUID, valveCommand
        );
    }

    public void onStop(View view) {
        byte[] valveCommand = {3};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.VALVE_CONTROLLER_SERVICE_UUID,
                BleAdapterService.COMMAND_CHARACTERISTIC_UUID, valveCommand
        );
    }

    public void onPause(View view) {
        byte[] valveCommand = {4};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.VALVE_CONTROLLER_SERVICE_UUID,
                BleAdapterService.COMMAND_CHARACTERISTIC_UUID, valveCommand
        );
    }

    public void onSendTP(View view) {

        /*byte hours = (byte) 1;
        byte minutes = (byte) 2;
        byte seconds = (byte) 3;
        byte dayOfTheWeek = (byte) 4;
        byte durationMsb = (byte) 5;
        byte durationLsb = (byte) 6;
        byte volumeMsb = (byte) 7;
        byte volumeLsb = (byte) 8;*/

        String[] ids = TimeZone.getAvailableIDs(+5 * 60 * 60 * 1000);
        SimpleTimeZone pdt = new SimpleTimeZone(+5 * 60 * 60 * 1000, ids[0]);

        Calendar calendar = new GregorianCalendar(pdt);
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        calendar.add(12, 2);

        //Set present time as data packet
        byte index = (byte) 1;
        byte dayOfTheWeek = (byte) calendar.get(Calendar.DAY_OF_WEEK);
        byte hours = (byte) calendar.get(Calendar.HOUR);
        if (calendar.get(Calendar.AM_PM) == 1) {
            hours = (byte) (calendar.get(Calendar.HOUR) + 12);
        }
        byte minutes = (byte) calendar.get(Calendar.MINUTE);
        byte seconds = (byte) calendar.get(Calendar.SECOND);
        int duration = 5;
        int volume = 5;
        int iDurationMSB = (duration / 256);
        int iDurationLSB = (duration % 256);
        byte bDurationMSB = (byte) iDurationMSB;
        byte bDurationLSB = (byte) iDurationLSB;
        int iVolumeMSB = (volume / 256);
        int iVolumeLSB = (volume % 256);
        byte bVolumeMSB = (byte) iVolumeMSB;
        byte bVolumeLSB = (byte) iVolumeLSB;

        byte[] timePoint = {index, dayOfTheWeek, hours, minutes, seconds, bDurationMSB, bDurationLSB, bVolumeMSB, bVolumeLSB};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.TIME_POINT_SERVICE_SERVICE_UUID,
                BleAdapterService.NEW_WATERING_TIME_POINT_CHARACTERISTIC_UUID, timePoint
        );
    }

    public void onBattery(View view) {


        bluetooth_le_adapter.readCharacteristic(
                BleAdapterService.BATTERY_SERVICE_SERVICE_UUID,
                BleAdapterService.BATTERY_LEVEL_CHARACTERISTIC_UUID
        );
    }

    public void onNoise(View view) {

    }

    public void onConnect(View view) {
        showMsg("onConnect");
        if (bluetooth_le_adapter != null) {
            if (bluetooth_le_adapter.connect(device_address)) {
                /*((Button) PeripheralControlActivity.this
                        .findViewById(R.id.connectButton)).setEnabled(false);*/
            } else {
                showMsg("onConnect: failed to connect");
            }
        } else {
            showMsg("onConnect: bluetooth_le_adapter=null");
        }
    }

    public void onBackPressed() {
        Log.d(Constants.TAG, "onBackPressed");
        back_requested = true;
        if (bluetooth_le_adapter.isConnected()) {
            try {
                bluetooth_le_adapter.disconnect();
            } catch (Exception e) {

            }
        } else {
            finish();
        }
    }

    private void setAlertLevel(int alert_level) {
        this.alert_level = alert_level;
        ((Button) this.findViewById(R.id.lowButton)).setTextColor(Color.parseColor("#000000"));
        ;
        ((Button) this.findViewById(R.id.midButton)).setTextColor(Color.parseColor("#000000"));
        ;
        ((Button) this.findViewById(R.id.highButton)).setTextColor(Color.parseColor("#000000"));
        ;

        switch (alert_level) {
            case 0:
                ((Button) this.findViewById(R.id.lowButton)).setTextColor(Color.parseColor("#FF0000"));
                ;
                break;

            case 1:
                ((Button) this.findViewById(R.id.midButton)).setTextColor(Color.parseColor("#FF0000"));
                break;

            case 2:
                ((Button) this.findViewById(R.id.highButton)).setTextColor(Color.parseColor("#FF0000"));
                break;
        }
    }


    private void showMsg(final String msg) {
        Log.e(Constants.TAG, msg);
      /*  runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView) findViewById(R.id.msgTextView)).setText(msg);
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(service_connection);
        bluetooth_le_adapter = null;
    }

    //End Mr. Vijay


    public void onSendTPDynamic(int hrs, int min, int sec, int dayOfWk, int durMsb, int durLsb, int volMsb, int volLsb) {
        byte hours = (byte) hrs;
        byte minutes = (byte) min;
        byte seconds = (byte) sec;
        byte dayOfTheWeek = (byte) dayOfWk;
        byte durationMsb = (byte) durMsb;
        byte durationLsb = (byte) durLsb;
        byte volumeMsb = (byte) volMsb;
        byte volumeLsb = (byte) volLsb;

        byte[] timePoint = {hours, minutes, seconds, dayOfTheWeek, durationMsb, durationLsb, volumeMsb, volumeLsb};
        bluetooth_le_adapter.writeCharacteristic(
                BleAdapterService.TIME_POINT_SERVICE_SERVICE_UUID,
                BleAdapterService.NEW_WATERING_TIME_POINT_CHARACTERISTIC_UUID, timePoint
        );
    }

    private void setViewSelectedRound() {
        tvSunEvent.setSelected(false);
        tvMonEvent.setSelected(false);
        tvTueEvent.setSelected(false);
        tvWedEvent.setSelected(false);
        tvThuEvent.setSelected(false);
        tvFriEvent.setSelected(false);
        tvSatEvent.setSelected(false);

        ivSunAdd.setVisibility(View.GONE);
        ivMonAdd.setVisibility(View.GONE);
        ivTueAdd.setVisibility(View.GONE);
        ivWedAdd.setVisibility(View.GONE);
        ivThuAdd.setVisibility(View.GONE);
        ivFriAdd.setVisibility(View.GONE);
        ivSatAdd.setVisibility(View.GONE);

        if (viewSelectedRound.getId() == R.id.tvSunEvent) {
            tvSunEvent.setSelected(true);
            if (sunTimePointsCount != 4) {
                ivSunAdd.setVisibility(View.VISIBLE);
            }

        }
        if (viewSelectedRound.getId() == R.id.tvMonEvent) {
            tvMonEvent.setSelected(true);
            if (monTimePointsCount != 4)
                ivMonAdd.setVisibility(View.VISIBLE);
        }
        if (viewSelectedRound.getId() == R.id.tvTueEvent) {
            tvTueEvent.setSelected(true);
            if (tueTimePointsCount != 4)
                ivTueAdd.setVisibility(View.VISIBLE);
        }
        if (viewSelectedRound.getId() == R.id.tvWedEvent) {
            tvWedEvent.setSelected(true);
            if (wedTimePointsCount != 4)
                ivWedAdd.setVisibility(View.VISIBLE);
        }
        if (viewSelectedRound.getId() == R.id.tvThuEvent) {
            tvThuEvent.setSelected(true);
            if (thuTimePointsCount != 4)
                ivThuAdd.setVisibility(View.VISIBLE);
        }
        if (viewSelectedRound.getId() == R.id.tvFriEvent) {
            tvFriEvent.setSelected(true);
            if (friTimePointsCount != 4)
                ivFriAdd.setVisibility(View.VISIBLE);
        }
        if (viewSelectedRound.getId() == R.id.tvSatEvent) {
            tvSatEvent.setSelected(true);
            if (satTimePointsCount != 4)
                ivSatAdd.setVisibility(View.VISIBLE);
        }
    }

    private void dialogTimePoints() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_time_points, null);

        dialogShowInfo = new Dialog(mContext);
        dialogShowInfo.setContentView(dialogView);
        dialogShowInfo.setCancelable(false);

        ImageView ivArrowUp = dialogShowInfo.findViewById(R.id.ivArrowUp);
        ImageView ivArrowDown = dialogShowInfo.findViewById(R.id.ivArrowDown);
        etInputTimePoint = dialogShowInfo.findViewById(R.id.etInputTimePoint);
        //Carry on with dialog counter
        etInputTimePoint.setText(etGetInputTimePoints);
        TextView tvDoneDialog = dialogShowInfo.findViewById(R.id.tvDoneDialog);
        final TextView tvCancelDialog = dialogShowInfo.findViewById(R.id.tvCancelDialog);

        ivArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePointsCounter >= 23) {
                    timePointsCounter = -1;
                }
                ++timePointsCounter;

                if (timePointsCounter >= 10) {
                    etInputTimePoint.setText(String.valueOf(timePointsCounter) + ":00");
                } else {
                    etInputTimePoint.setText("0" + String.valueOf(timePointsCounter) + ":00");
                }

            }
        });

        ivArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timePointsCounter <= 0) {
                    timePointsCounter = 24;
                }

                --timePointsCounter;

                if (timePointsCounter >= 10) {
                    etInputTimePoint.setText(String.valueOf(timePointsCounter) + ":00");
                } else {
                    etInputTimePoint.setText("0" + String.valueOf(timePointsCounter) + ":00");
                }

            }
        });

        tvDoneDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneTimePointSelection();
            }
        });

        tvCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogShowInfo.dismiss();
            }
        });
        //Show dialog in Landscape mode
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        Window windowAlDl = dialogShowInfo.getWindow();

        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        windowAlDl.setAttributes(layoutParams);
        dialogShowInfo.show();

    }

    private void doneTimePointSelection() {
        dialogShowInfo.dismiss();

        etGetInputTimePoints = etInputTimePoint.getText().toString();
        etGetInputTimePointsInt = Integer.parseInt(etGetInputTimePoints.substring(0, 2));

        if (viewSelectedRound.getId() == R.id.tvSunEvent) {
            ++sunTimePointsCount;

            if (sunTimePointsCount == 4) {
                ivSunAdd.setVisibility(View.GONE);
            }

            if (tvSunFirst.getVisibility() != View.VISIBLE) {
                tvSunFirst.setVisibility(View.VISIBLE);
                tvSunFirst.setText(etGetInputTimePoints);

                listTimePntsSun.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSunSecond.getVisibility() != View.VISIBLE) {
                tvSunSecond.setVisibility(View.VISIBLE);
                tvSunSecond.setText(etGetInputTimePoints);

                listTimePntsSun.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSunThird.getVisibility() != View.VISIBLE) {
                tvSunThird.setVisibility(View.VISIBLE);
                tvSunThird.setText(etGetInputTimePoints);

                listTimePntsSun.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSunFourth.getVisibility() != View.VISIBLE) {
                tvSunFourth.setVisibility(View.VISIBLE);
                tvSunFourth.setText(etGetInputTimePoints);

                listTimePntsSun.add(etGetInputTimePointsInt);

                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvMonEvent) {
            ++monTimePointsCount;

            if (monTimePointsCount == 4) {
                ivMonAdd.setVisibility(View.GONE);
            }

            if (tvMonFirst.getVisibility() != View.VISIBLE) {
                tvMonFirst.setVisibility(View.VISIBLE);
                tvMonFirst.setText(etGetInputTimePoints);

                listTimePntsMon.add(etGetInputTimePointsInt);
                return;
            }
            if (tvMonSecond.getVisibility() != View.VISIBLE) {
                tvMonSecond.setVisibility(View.VISIBLE);
                tvMonSecond.setText(etGetInputTimePoints);

                listTimePntsMon.add(etGetInputTimePointsInt);
                return;
            }
            if (tvMonThird.getVisibility() != View.VISIBLE) {
                tvMonThird.setVisibility(View.VISIBLE);
                tvMonThird.setText(etGetInputTimePoints);

                listTimePntsMon.add(etGetInputTimePointsInt);
                return;
            }
            if (tvMonFourth.getVisibility() != View.VISIBLE) {
                tvMonFourth.setVisibility(View.VISIBLE);
                tvMonFourth.setText(etGetInputTimePoints);

                listTimePntsMon.add(etGetInputTimePointsInt);
                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvTueEvent) {
            ++tueTimePointsCount;

            if (tueTimePointsCount == 4) {
                ivTueAdd.setVisibility(View.GONE);
            }

            if (tvTueFirst.getVisibility() != View.VISIBLE) {
                tvTueFirst.setVisibility(View.VISIBLE);
                tvTueFirst.setText(etGetInputTimePoints);

                listTimePntsTue.add(etGetInputTimePointsInt);
                return;
            }
            if (tvTueSecond.getVisibility() != View.VISIBLE) {
                tvTueSecond.setVisibility(View.VISIBLE);
                tvTueSecond.setText(etGetInputTimePoints);

                listTimePntsTue.add(etGetInputTimePointsInt);
                return;
            }
            if (tvTueThird.getVisibility() != View.VISIBLE) {
                tvTueThird.setVisibility(View.VISIBLE);
                tvTueThird.setText(etGetInputTimePoints);

                listTimePntsTue.add(etGetInputTimePointsInt);
                return;
            }
            if (tvTueFourth.getVisibility() != View.VISIBLE) {
                tvTueFourth.setVisibility(View.VISIBLE);
                tvTueFourth.setText(etGetInputTimePoints);

                listTimePntsTue.add(etGetInputTimePointsInt);
                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvWedEvent) {
            ++wedTimePointsCount;

            if (wedTimePointsCount == 4) {
                ivWedAdd.setVisibility(View.GONE);
            }

            if (tvWedFirst.getVisibility() != View.VISIBLE) {
                tvWedFirst.setVisibility(View.VISIBLE);
                tvWedFirst.setText(etGetInputTimePoints);

                listTimePntsWed.add(etGetInputTimePointsInt);
                return;
            }
            if (tvWedSecond.getVisibility() != View.VISIBLE) {
                tvWedSecond.setVisibility(View.VISIBLE);
                tvWedSecond.setText(etGetInputTimePoints);

                listTimePntsWed.add(etGetInputTimePointsInt);
                return;
            }
            if (tvWedThird.getVisibility() != View.VISIBLE) {
                tvWedThird.setVisibility(View.VISIBLE);
                tvWedThird.setText(etGetInputTimePoints);

                listTimePntsWed.add(etGetInputTimePointsInt);
                return;
            }
            if (tvWedFourth.getVisibility() != View.VISIBLE) {
                tvWedFourth.setVisibility(View.VISIBLE);
                tvWedFourth.setText(etGetInputTimePoints);

                listTimePntsWed.add(etGetInputTimePointsInt);
                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvThuEvent) {
            ++thuTimePointsCount;

            if (thuTimePointsCount == 4) {
                ivThuAdd.setVisibility(View.GONE);
            }

            if (tvThuFirst.getVisibility() != View.VISIBLE) {
                tvThuFirst.setVisibility(View.VISIBLE);
                tvThuFirst.setText(etGetInputTimePoints);

                listTimePntsThu.add(etGetInputTimePointsInt);
                return;
            }
            if (tvThuSecond.getVisibility() != View.VISIBLE) {
                tvThuSecond.setVisibility(View.VISIBLE);
                tvThuSecond.setText(etGetInputTimePoints);

                listTimePntsThu.add(etGetInputTimePointsInt);
                return;
            }
            if (tvThuThird.getVisibility() != View.VISIBLE) {
                tvThuThird.setVisibility(View.VISIBLE);
                tvThuThird.setText(etGetInputTimePoints);

                listTimePntsThu.add(etGetInputTimePointsInt);
                return;
            }
            if (tvThuFourth.getVisibility() != View.VISIBLE) {
                tvThuFourth.setVisibility(View.VISIBLE);
                tvThuFourth.setText(etGetInputTimePoints);

                listTimePntsThu.add(etGetInputTimePointsInt);
                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvFriEvent) {
            ++friTimePointsCount;

            if (friTimePointsCount == 4) {
                ivFriAdd.setVisibility(View.GONE);
            }

            if (tvFriFirst.getVisibility() != View.VISIBLE) {
                tvFriFirst.setVisibility(View.VISIBLE);
                tvFriFirst.setText(etGetInputTimePoints);

                listTimePntsFri.add(etGetInputTimePointsInt);
                return;
            }
            if (tvFriSecond.getVisibility() != View.VISIBLE) {
                tvFriSecond.setVisibility(View.VISIBLE);
                tvFriSecond.setText(etGetInputTimePoints);

                listTimePntsFri.add(etGetInputTimePointsInt);
                return;
            }
            if (tvFriThird.getVisibility() != View.VISIBLE) {
                tvFriThird.setVisibility(View.VISIBLE);
                tvFriThird.setText(etGetInputTimePoints);

                listTimePntsFri.add(etGetInputTimePointsInt);
                return;
            }
            if (tvFriFourth.getVisibility() != View.VISIBLE) {
                tvFriFourth.setVisibility(View.VISIBLE);
                tvFriFourth.setText(etGetInputTimePoints);

                listTimePntsFri.add(etGetInputTimePointsInt);
                return;
            }
        }


        if (viewSelectedRound.getId() == R.id.tvSatEvent) {
            ++satTimePointsCount;

            if (satTimePointsCount == 4) {
                ivSatAdd.setVisibility(View.GONE);
            }

            if (tvSatFirst.getVisibility() != View.VISIBLE) {
                tvSatFirst.setVisibility(View.VISIBLE);
                tvSatFirst.setText(etGetInputTimePoints);

                listTimePntsSat.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSatSecond.getVisibility() != View.VISIBLE) {
                tvSatSecond.setVisibility(View.VISIBLE);
                tvSatSecond.setText(etGetInputTimePoints);

                listTimePntsSat.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSatThird.getVisibility() != View.VISIBLE) {
                tvSatThird.setVisibility(View.VISIBLE);
                tvSatThird.setText(etGetInputTimePoints);

                listTimePntsSat.add(etGetInputTimePointsInt);
                return;
            }
            if (tvSatFourth.getVisibility() != View.VISIBLE) {
                tvSatFourth.setVisibility(View.VISIBLE);
                tvSatFourth.setText(etGetInputTimePoints);

                listTimePntsSat.add(etGetInputTimePointsInt);
                return;
            }
        }

    }

    private void visibleCursorSoftKeyboard() {
        etDischargePoints.setFocusableInTouchMode(true);
        etDurationPlan.setFocusableInTouchMode(true);
        etQuantityPlan.setFocusableInTouchMode(true);

        etDischargePoints.setCursorVisible(true);
        etDurationPlan.setCursorVisible(true);
        etQuantityPlan.setCursorVisible(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivSunAdd:
                dialogTimePoints();
                break;
            case R.id.ivMonAdd:
                dialogTimePoints();
                break;
            case R.id.ivTueAdd:
                dialogTimePoints();
                break;
            case R.id.ivWedAdd:
                dialogTimePoints();
                break;
            case R.id.ivThuAdd:
                dialogTimePoints();
                break;
            case R.id.ivFriAdd:
                dialogTimePoints();
                break;
            case R.id.ivSatAdd:
                dialogTimePoints();
                break;
            default:
                dialogDeleteEditTPts(v);

        }
    }

    private void dialogDeleteEditTPts(View view) {
        final int clickedItemId = view.getId();
        String clickedItemText = ((TextView) view).getText().toString();

        AlertDialog.Builder alBu = new AlertDialog.Builder(mContext);
        alBu.setTitle(clickedItemText);
        alBu.setCancelable(false);
        alBu.setMessage("Your action with above time point?");
        alBu.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alBu.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteTPnts(clickedItemId);
            }
        });
        alBu.create().show();
    }

    private void deleteTPnts(int clickedItemId) {
        {
            Toast.makeText(mContext, "Time point deleted", Toast.LENGTH_SHORT).show();
            switch (clickedItemId) {
                case R.id.tvSunFirst:
                    tvSunFirst.setVisibility(View.GONE);
                    if (sunTimePointsCount == 4) {
                        ivSunAdd.setVisibility(View.VISIBLE);
                    }
                    sunTimePointsCount--;
                    break;
                case R.id.tvSunSecond:
                    tvSunSecond.setVisibility(View.GONE);
                    if (sunTimePointsCount == 4) {
                        ivSunAdd.setVisibility(View.VISIBLE);
                    }
                    sunTimePointsCount--;
                    break;
                case R.id.tvSunThird:
                    tvSunThird.setVisibility(View.GONE);
                    if (sunTimePointsCount == 4) {
                        ivSunAdd.setVisibility(View.VISIBLE);
                    }
                    sunTimePointsCount--;
                    break;
                case R.id.tvSunFourth:
                    tvSunFourth.setVisibility(View.GONE);
                    if (sunTimePointsCount == 4) {
                        ivSunAdd.setVisibility(View.VISIBLE);
                    }
                    sunTimePointsCount--;
                    break;

                case R.id.tvMonFirst:
                    tvMonFirst.setVisibility(View.GONE);
                    if (monTimePointsCount == 4) {
                        ivMonAdd.setVisibility(View.VISIBLE);
                    }
                    monTimePointsCount--;
                    break;
                case R.id.tvMonSecond:
                    tvMonSecond.setVisibility(View.GONE);
                    if (monTimePointsCount == 4) {
                        ivMonAdd.setVisibility(View.VISIBLE);
                    }
                    monTimePointsCount--;
                    break;
                case R.id.tvMonThird:
                    tvMonThird.setVisibility(View.GONE);
                    if (monTimePointsCount == 4) {
                        ivMonAdd.setVisibility(View.VISIBLE);
                    }
                    monTimePointsCount--;
                    break;
                case R.id.tvMonFourth:
                    tvMonFourth.setVisibility(View.GONE);
                    if (monTimePointsCount == 4) {
                        ivMonAdd.setVisibility(View.VISIBLE);
                    }
                    monTimePointsCount--;
                    break;

                case R.id.tvTueFirst:
                    tvTueFirst.setVisibility(View.GONE);
                    if (tueTimePointsCount == 4) {
                        ivTueAdd.setVisibility(View.VISIBLE);
                    }
                    tueTimePointsCount--;
                    break;
                case R.id.tvTueSecond:
                    tvTueSecond.setVisibility(View.GONE);
                    if (tueTimePointsCount == 4) {
                        ivTueAdd.setVisibility(View.VISIBLE);
                    }
                    tueTimePointsCount--;
                    break;
                case R.id.tvTueThird:
                    tvTueThird.setVisibility(View.GONE);
                    if (tueTimePointsCount == 4) {
                        ivTueAdd.setVisibility(View.VISIBLE);
                    }
                    tueTimePointsCount--;
                    break;
                case R.id.tvTueFourth:
                    tvTueFourth.setVisibility(View.GONE);
                    if (tueTimePointsCount == 4) {
                        ivTueAdd.setVisibility(View.VISIBLE);
                    }
                    tueTimePointsCount--;
                    break;

                case R.id.tvWedFirst:
                    tvWedFirst.setVisibility(View.GONE);
                    if (wedTimePointsCount == 4) {
                        ivWedAdd.setVisibility(View.VISIBLE);
                    }
                    wedTimePointsCount--;
                    break;
                case R.id.tvWedSecond:
                    tvWedSecond.setVisibility(View.GONE);
                    if (wedTimePointsCount == 4) {
                        ivWedAdd.setVisibility(View.VISIBLE);
                    }
                    wedTimePointsCount--;
                    break;
                case R.id.tvWedThird:
                    tvWedThird.setVisibility(View.GONE);
                    if (wedTimePointsCount == 4) {
                        ivWedAdd.setVisibility(View.VISIBLE);
                    }
                    wedTimePointsCount--;
                    break;
                case R.id.tvWedFourth:
                    tvWedFourth.setVisibility(View.GONE);
                    if (wedTimePointsCount == 4) {
                        ivWedAdd.setVisibility(View.VISIBLE);
                    }
                    wedTimePointsCount--;
                    break;

                case R.id.tvThuFirst:
                    tvThuFirst.setVisibility(View.GONE);
                    if (thuTimePointsCount == 4) {
                        ivThuAdd.setVisibility(View.VISIBLE);
                    }
                    thuTimePointsCount--;
                    break;
                case R.id.tvThuSecond:
                    tvThuSecond.setVisibility(View.GONE);
                    if (thuTimePointsCount == 4) {
                        ivThuAdd.setVisibility(View.VISIBLE);
                    }
                    thuTimePointsCount--;
                    break;
                case R.id.tvThuThird:
                    tvThuThird.setVisibility(View.GONE);
                    if (thuTimePointsCount == 4) {
                        ivThuAdd.setVisibility(View.VISIBLE);
                    }
                    thuTimePointsCount--;
                    break;
                case R.id.tvThuFourth:
                    tvThuFourth.setVisibility(View.GONE);
                    if (thuTimePointsCount == 4) {
                        ivThuAdd.setVisibility(View.VISIBLE);
                    }
                    thuTimePointsCount--;
                    break;

                case R.id.tvFriFirst:
                    tvFriFirst.setVisibility(View.GONE);
                    if (friTimePointsCount == 4) {
                        ivFriAdd.setVisibility(View.VISIBLE);
                    }
                    friTimePointsCount--;
                    break;
                case R.id.tvFriSecond:
                    tvFriSecond.setVisibility(View.GONE);
                    if (friTimePointsCount == 4) {
                        ivFriAdd.setVisibility(View.VISIBLE);
                    }
                    friTimePointsCount--;
                    break;
                case R.id.tvFriThird:
                    tvFriThird.setVisibility(View.GONE);
                    if (friTimePointsCount == 4) {
                        ivFriAdd.setVisibility(View.VISIBLE);
                    }
                    friTimePointsCount--;
                    break;
                case R.id.tvFriFourth:
                    tvFriFourth.setVisibility(View.GONE);
                    if (friTimePointsCount == 4) {
                        ivFriAdd.setVisibility(View.VISIBLE);
                    }
                    friTimePointsCount--;
                    break;

                case R.id.tvSatFirst:
                    tvSatFirst.setVisibility(View.GONE);
                    if (satTimePointsCount == 4) {
                        ivSatAdd.setVisibility(View.VISIBLE);
                    }
                    satTimePointsCount--;
                    break;
                case R.id.tvSatSecond:
                    tvSatSecond.setVisibility(View.GONE);
                    if (satTimePointsCount == 4) {
                        ivSatAdd.setVisibility(View.VISIBLE);
                    }
                    satTimePointsCount--;
                    break;
                case R.id.tvSatThird:
                    tvSatThird.setVisibility(View.GONE);
                    if (satTimePointsCount == 4) {
                        ivSatAdd.setVisibility(View.VISIBLE);
                    }
                    satTimePointsCount--;
                    break;
                case R.id.tvSatFourth:
                    tvSatFourth.setVisibility(View.GONE);
                    if (satTimePointsCount == 4) {
                        ivSatAdd.setVisibility(View.VISIBLE);
                    }
                    satTimePointsCount--;
                    break;
            }
        }
    }

//End of Class
}
