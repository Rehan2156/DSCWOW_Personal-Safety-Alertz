package com.example.personal_safety_battery_management;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CONTACT_TABLE = "CONTACT_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CONTACT_NAME = "CONTACT_NAME";
    public static final String COLUMN_CONTACT_PHONE = "CONTACT_PHONE";
    public static final String COLUMN_ACTIVE_CONTACT = "ACTIVE_CONTACT";
    public static final String ALERT_TABLE = "ALERT_TABLE";
    public static final String ALERT_ID = "ALERT_ID";
    public static final String BATTERY = "BATTERY";
    public static final String LOCATION = "LOCATION";
    public static final String ALERT_MSG = "ALERT_MSG";
    public static final String CREATED_AT = "CREATED_AT";
    public static final String CONTACT_NAME_1 = "CONTACT_NAME1";
    public static final String CONTACT_PHONE_1 = "CONTACT_PHONE1";
    public static final String CONTACT_NAME_2 = "CONTACT_NAME2";
    public static final String CONTACT_PHONE_2 = "CONTACT_PHONE2";
    public static final String CONTACT_NAME_3 = "CONTACT_NAME3";
    public static final String CONTACT_PHONE_3 = "CONTACT_PHONE3";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "contact.db", null, 1);
    }

    //called when first time database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CONTACT_TABLE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_CONTACT_NAME + " TEXT, " + COLUMN_CONTACT_PHONE + " TEXT)";
        db.execSQL(createTableStatement);
        String createAlertTable = "CREATE TABLE " + ALERT_TABLE + "(" + ALERT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BATTERY + " INT," + LOCATION + " TEXT, " + ALERT_MSG + " TEXT," + CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " + CONTACT_NAME_1 + " TEXT, " + CONTACT_PHONE_1 + " TEXT, " + CONTACT_NAME_2 + " TEXT, " + CONTACT_PHONE_2 + " TEXT, " + CONTACT_NAME_3 + " TEXT, " + CONTACT_PHONE_3 + " TEXT)";
        db.execSQL(createAlertTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(ContactModel contactModel,int id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        String checkQuery="SELECT "+COLUMN_CONTACT_PHONE+" FROM "+CONTACT_TABLE+" WHERE "+COLUMN_ID+"="+id;
        Cursor cursor = db.rawQuery(checkQuery,null);
        boolean exists = (cursor.getCount() > 0);
        long insert;
        cv.put(COLUMN_CONTACT_NAME,contactModel.getName());
        cv.put(COLUMN_CONTACT_PHONE,contactModel.getPhone());
        if(exists){
            insert= db.update(CONTACT_TABLE,cv,COLUMN_ID+"= ? ",new String[]{String.valueOf(id)});
        }
        else {
            insert = db.insert(CONTACT_TABLE, null, cv);
            System.out.println("Adding one "+contactModel.getName());
        }
        return insert==-1?false:true;
    }

    public boolean addOneAlert(AlertModel alertModel){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BATTERY,alertModel.getBattery());
        cv.put(LOCATION,alertModel.getLocation());
        cv.put(ALERT_MSG,alertModel.getAlertMsg());
        cv.put(CONTACT_NAME_1,alertModel.getContactName1());
        cv.put(CONTACT_PHONE_1,alertModel.getContactPhone1());
        cv.put(CONTACT_NAME_2,alertModel.getContactName2());
        cv.put(CONTACT_PHONE_2,alertModel.getContactPhone2());
        cv.put(CONTACT_NAME_3,alertModel.getContactName3());
        cv.put(CONTACT_PHONE_3,alertModel.getContactPhone3());

        long insert = db.insert(ALERT_TABLE, null, cv);
        return insert==-1?false:true;
    }

    public List<ContactModel> getEveryone(){
        List<ContactModel> returnList = new ArrayList<>();

        String queryString= "SELECT * FROM "+CONTACT_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        //check if database contains entries
        if(cursor.moveToFirst()){
            //loop through cursor and create new contact object
            do{
                int contactID = cursor.getInt(0);
                String contactName = cursor.getString(1);
                String contactPhone = cursor.getString(2);
                System.out.println("getting "+contactPhone);
                ContactModel newContact = new ContactModel(contactID,contactName,contactPhone);
                returnList.add(newContact);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return returnList;
    }

    public List<AlertModel> getAllAlerts(){
        List<AlertModel> returnList = new ArrayList<>();

        String queryString= "SELECT * FROM "+ALERT_TABLE;
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);
        //check if database contains entries
        if(cursor.moveToFirst()){
            //loop through cursor and create new contact object
            do{
                int alertID = cursor.getInt(0);
                int battery = cursor.getInt(1);
                String loc = cursor.getString(2);
                String alertMsg = cursor.getString(3);
                String createdAt = cursor.getString(4);
                String contactName1 = cursor.getString(5);
                String contactPhone1 = cursor.getString(6);
                String contactName2 = cursor.getString(7);
                String contactPhone2 = cursor.getString(8);
                String contactName3 = cursor.getString(9);
                String contactPhone3 = cursor.getString(10);

                AlertModel newAlert = new AlertModel(alertID,battery,loc,alertMsg,createdAt,contactName1,contactName2,contactName3,contactPhone1,contactPhone2,contactPhone3);
                returnList.add(newAlert);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return returnList;
    }
}
