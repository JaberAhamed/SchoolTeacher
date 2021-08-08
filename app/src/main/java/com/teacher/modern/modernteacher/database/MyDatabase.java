package com.teacher.modern.modernteacher.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String dataBaseName = "AMS_Teacher";

    private static final int versionNumber = 1;


    private Context context;

    //Table Create SQL
    private static final String createTable_LoginInfo ="create table LoginInfo (email varchar(200) primary key, password varchar(200),userID Integer,android_id varchar(200));";

    private static final String createTable_LocalNotification = "create table LocalNotification (lastNoticeSeen varchar(200));";

    public MyDatabase(Context context) {
        super(context, dataBaseName, null, versionNumber);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createTable_LoginInfo);
        sqLiteDatabase.execSQL(createTable_LocalNotification);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long updateLastNoticeSeen(String lastNoticeSeen){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from LocalNotification;",null);

        if(cursor.getCount()!=0){

            ContentValues contentValues = new ContentValues();

            contentValues.put("lastNoticeSeen", lastNoticeSeen);

            sqLiteDatabase.update("LocalNotification", contentValues, null, null);

            return 0;
        }
        else{
            ContentValues contentValues = new ContentValues();

            contentValues.put("lastNoticeSeen", lastNoticeSeen);

            long rowID = sqLiteDatabase.insert("LocalNotification",null,contentValues);

            return rowID;
        }


    }

    public  String getLastNoticeSeen(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LocalNotification;",null);

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        else{
            return "Not found";
        }
    }

    public long insertLoginInfo(String email, String password,long userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("userID", userID);
       // contentValues.put("usweType", usweType);
        //contentValues.put("android_id", "");

        long rowID = sqLiteDatabase.insert("LoginInfo",null,contentValues);

        //Toast.makeText(context,"LoginInfo is inserted where UserId: "+UserId,Toast.LENGTH_LONG).show();

        return rowID;
    }
    public  Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoginInfo;",null);
        return cursor;
    }

    public Integer deleteGuardian (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("LoginInfo",
                "email = ? ",
                new String[] { id });
    }


    public  String getEmail(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoginInfo;",null);

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        else{
            return "Not found";
        }
    }

    public  String getPassword(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoginInfo;",null);

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getString(1);
        }
        else{
            return "Not found";
        }
    }

    public  String getUserType(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoginInfo;",null);

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getString(2);
        }
        else{
            return "Not found";
        }
    }

    public  String getAppId(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoginInfo;",null);

        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getString(3);
        }
        else{
            return "Not found";
        }
    }
}
