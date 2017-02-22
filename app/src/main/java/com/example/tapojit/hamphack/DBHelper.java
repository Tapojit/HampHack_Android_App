package com.example.tapojit.hamphack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tapojit on 1/5/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notifications.db";
    public static final String TABLE_NOTIFICATIONS = "notifications";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ID2 = "_id1";
    public static final String COLUMN_DETAILS = "details";
    public static final String TABLE_ANNOUNCEMENTS = "announce";
    public static final String COLUMN_ANNOUNCE = "ann";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NOTIFICATIONS+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_DETAILS+" TEXT);";
        String query2="CREATE TABLE "+TABLE_ANNOUNCEMENTS+"("+COLUMN_ID2+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_ANNOUNCE+" TEXT);";
        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ANNOUNCEMENTS);
        onCreate(db);
    }

    public void addDetail(Notifications notifications){
        ContentValues values=new ContentValues();
        values.put(COLUMN_DETAILS,notifications.get_details());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_NOTIFICATIONS,null,values);
        db.close();
    }
    public void addAnnoun(Notifications notifications){
        ContentValues values=new ContentValues();
        values.put(COLUMN_ANNOUNCE,notifications.get_details());
        SQLiteDatabase db=getWritableDatabase();
        db.insert(TABLE_ANNOUNCEMENTS,null,values);
        db.close();
    }

    public Boolean containsDetailA(String detail){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_ANNOUNCEMENTS+" WHERE 1";
        Cursor recordSet=db.rawQuery(query,null);
        recordSet.moveToFirst();
        Boolean present=false;

        while(!recordSet.isAfterLast()){
            if(recordSet.getString(recordSet.getColumnIndex("ann")).equals(detail)){
                present=true;
                break;
            }
            else recordSet.moveToNext();
        }

        db.close();
        return present;
    }

    public Boolean containsDetail(String detail){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NOTIFICATIONS+" WHERE 1";
        Cursor recordSet=db.rawQuery(query,null);
        recordSet.moveToFirst();
        Boolean present=false;

        while(!recordSet.isAfterLast()){
            if(recordSet.getString(recordSet.getColumnIndex("details")).equals(detail)){
                present=true;
                break;
            }
            else recordSet.moveToNext();
        }

        db.close();
        return present;
    }
}
