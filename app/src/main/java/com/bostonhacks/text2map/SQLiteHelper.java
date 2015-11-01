package com.bostonhacks.text2map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by David Liu on 10/31/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME  = "Text2Map";
    public static final String TABLE_NAME = "dictionary";
    public static final String TABLE_NAME_USER = "history";
    public static final String[] columns = {
            "_ID","Start","End","Response","Comments"
    };
    public static final String[] columns_user = {
            "_ID","lat","lon","time"
    };
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    columns[0] + " INTEGER PRIMARY KEY, " +
                    columns[1] + " TEXT," +
                    columns[2] + " TEXT," +
                    columns[3] + " TEXT,"+
                    columns[4] + " TEXT);";

    private static final String TABLE_CREATE_2 =
            "CREATE TABLE " + TABLE_NAME_USER + " (" +
                    columns_user[0] + " INTEGER PRIMARY KEY, " +
                    columns_user[1] + " TEXT," +
                    columns_user[2] + " TEXT," +
                    columns_user[3] + " TEXT);";

    SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
        sqLiteDatabase.execSQL(TABLE_CREATE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + i + " to "
                        + i1 + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USER);
        onCreate(sqLiteDatabase);
    }
}
