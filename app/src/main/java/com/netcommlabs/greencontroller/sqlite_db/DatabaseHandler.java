package com.netcommlabs.greencontroller.sqlite_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netcommlabs.aroundusp.models.ModlCalendarWholeDetails;
import com.netcommlabs.aroundusp.models.ModlCalndAUnwPersonalJobs;

import java.util.List;

/**
 * Created by Android on 7/26/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "dbGreenController";
    // Table for Session plan info
    private static final String TABLE_AU_NTW_JOBS = "tblSesnPlan";
    // Contacts Table Columns names
    private static final String COLUMN_LIST_TIME_SLOTS = "listWithObjects";

    ModlCalendarWholeDetails modlCalendarWholeDetails;

    List<String> listTimeSlots;
    List<String> listDayoff;
    List<ModlCalndAUnwPersonalJobs> listAUNtwJobs;
    List<ModlCalndAUnwPersonalJobs> listPersonalJobs;

    String inputStringTimeSlots, inputStringDayOff, inputStringAUNtwJobs, inputStringPrsnalJobs;
    Gson gson;
    byte[] calndrWholeObjBytes;
    SQLiteDatabase db;


    public DatabaseHandler(Context context, ModlCalendarWholeDetails modlCalendarWholeDetails) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.modlCalendarWholeDetails = modlCalendarWholeDetails;
        gson = new Gson();
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_AU_NTW_JOBS = "CREATE TABLE " + TABLE_AU_NTW_JOBS + "("
                + COLUMN_LIST_TIME_SLOTS + /*" TEXT," + LIST_DAYOFF_SP + " TEXT," + LIST_AU_NTWK_JOBS + " TEXT," + LIST_PERSONAL_JOBS +*/ " TEXT" + ")";

      /*  //-----Creating table for AU ntw Jobs-----
        String CREATE_TABLE_AU_NTW_JOBS = "CREATE TABLE " + TABLE_AU_NTW_JOBS + "("
                + JOB_ID + " TEXT," + JOB_DATE + " TEXT," + JOB_TIME + " TEXT," + PARENT_TRADE + " TEXT," + SUB_TRADE + " TEXT," + CHILD_TRADE + " TEXT," + CLIENT_NAME + " TEXT," + CONTACT_NO + " TEXT," + ADDRESS + " TEXT," + QUE_BUDGET + " TEXT," + QUE_PRE_TIME + " TEXT," + QUE_PRE_DATE + " TEXT," + QUE_ANY_ADDITIONAL + " TEXT," + QUE_PROMO_CODE + " TEXT" + ")";

//-----Creating table for PERSONAL jobs -----
        String CREATE_TABLE_PERSONAL_JOBS = "CREATE TABLE " + TABLE_PERSONAL_JOBS + "(" + JOB_DATE + " TEXT," + JOB_TIME + " TEXT," + CLIENT_NAME + " TEXT," + CONTACT_NO + " TEXT," + ADDRESS + " TEXT," + DESCRIPTION + " TEXT" + ")";
*/

        db.execSQL(CREATE_TABLE_AU_NTW_JOBS);
        //db.execSQL(CREATE_TABLE_AU_NTW_JOBS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AU_NTW_JOBS);

        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_AU_NTW_JOBS);

        // Create tables again
        onCreate(db);
    }

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void closeDatabase() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public void insertDataIntoDB() {

        calndrWholeObjBytes = gson.toJson(modlCalendarWholeDetails).getBytes();


        /*listTimeSlots = modlCalendarWholeDetails.getListTimeSlots();
        listDayoff = modlCalendarWholeDetails.getListDayOffSP();
        listAUNtwJobs = modlCalendarWholeDetails.getListAUAndPersonalJobs();
        listPersonalJobs = modlCalendarWholeDetails.getListPersonalJobs();

        inputStringTimeSlots = gson.toJson(listTimeSlots);
        inputStringDayOff = gson.toJson(listDayoff);
        inputStringAUNtwJobs = gson.toJson(listAUNtwJobs);
        inputStringPrsnalJobs = gson.toJson(listPersonalJobs);*/

        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_TIME_SLOTS, calndrWholeObjBytes);

       /* values.put(COLUMN_LIST_TIME_SLOTS, inputStringTimeSlots);
        values.put(LIST_DAYOFF_SP, inputStringDayOff);
        values.put(LIST_AU_NTWK_JOBS, inputStringAUNtwJobs);
        values.put(LIST_PERSONAL_JOBS, inputStringPrsnalJobs);*/

// Inserting Row
        db.insert(TABLE_AU_NTW_JOBS, null, values);
    }

    public ModlCalendarWholeDetails getWholeCalendarDataFrmDb() {

        String selectQuery = "SELECT  * FROM " + TABLE_AU_NTW_JOBS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();

               /* Type typeStrnTimeSltsDayoff = new TypeToken<List<String>>() {
                }.getType();
                Type typeStrnAUNtwAndPrsonalJobs = new TypeToken<List<ModlCalndAUnwPersonalJobs>>() {
                }.getType();

                String stringOfTimeSlotsList = cursor.getString(cursor.getColumnIndex(COLUMN_LIST_TIME_SLOTS));
                List<String> listTimeSlots = gson.fromJson(stringOfTimeSlotsList, typeStrnTimeSltsDayoff);

                String stringOfDayOffList = cursor.getString(cursor.getColumnIndex(LIST_DAYOFF_SP));
                List<String> listSPDayOff = gson.fromJson(stringOfDayOffList, typeStrnTimeSltsDayoff);

                String stringOfAUNtwrJobs = cursor.getString(cursor.getColumnIndex(LIST_AU_NTWK_JOBS));
                List<ModlCalndAUnwPersonalJobs> listOfAUNtwrJobs = gson.fromJson(stringOfAUNtwrJobs, typeStrnAUNtwAndPrsonalJobs);

                String stringOfPrsnlJobs = cursor.getString(cursor.getColumnIndex(LIST_PERSONAL_JOBS));
                List<ModlCalndAUnwPersonalJobs> listOfPrsnlJobs = gson.fromJson(stringOfPrsnlJobs, typeStrnAUNtwAndPrsonalJobs);*/

        byte[] blob = cursor.getBlob(cursor.getColumnIndex(COLUMN_LIST_TIME_SLOTS));
        String json = new String(blob);
        modlCalendarWholeDetails = gson.fromJson(json, new TypeToken<ModlCalendarWholeDetails>() {
        }.getType());

        //modlCalendarWholeDetails = new ModlCalendarWholeDetails(listTimeSlots, listSPDayOff, listOfAUNtwrJobs, listOfPrsnlJobs);
        // get the data into array, or class variable

        cursor.close();
        return modlCalendarWholeDetails;
    }

    public void deleteAllRecordFromTable() {
        db.execSQL("delete from " + TABLE_AU_NTW_JOBS);
    }

}
