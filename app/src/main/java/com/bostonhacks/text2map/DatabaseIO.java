package com.bostonhacks.text2map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by David Liu on 10/31/2015.
 */
public class DatabaseIO {
    static SQLiteHelper mDbHelper;
    DatabaseIO(Context c){
        mDbHelper = new SQLiteHelper(c);
    }

    public long writeToTable(Direction direction){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.columns[1], direction.from);
        values.put(SQLiteHelper.columns[2], direction.to);
        values.put(SQLiteHelper.columns[3], direction.response);
        switch (direction.type){
            case R:
                values.put(SQLiteHelper.columns[4], "r");
                break;
            case P:
                values.put(SQLiteHelper.columns[4], "p");
                break;
            case W:
                values.put(SQLiteHelper.columns[4], "w");
                break;
            case F:
                values.put(SQLiteHelper.columns[4], "f");
                break;
        }

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SQLiteHelper.TABLE_NAME,
                "null",
                values);
        db.close();
        return newRowId;
    }

    public boolean updateTable(Direction direction){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.columns[3], direction.response);

        // Which row to update, based on the ID
        String selection = SQLiteHelper.columns[0] + " = " + String.valueOf(direction.getID());

        int count = db.update(
                SQLiteHelper.TABLE_NAME,
                values,
                selection,
                null);

        db.close();
        return (count > 0);
    }

    public String queryBy(int id){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        Cursor cursor = db.query(SQLiteHelper.TABLE_NAME, new String[]{SQLiteHelper.columns[3]}, SQLiteHelper.columns[0] + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        db.close();

        return cursor.getString(0);
    }

    public long recordHistory(UserLocation location){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.columns_user[1], location.lat);
        values.put(SQLiteHelper.columns_user[2], location.lon);
        values.put(SQLiteHelper.columns_user[3], location.current_time);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SQLiteHelper.TABLE_NAME_USER,
                "null",
                values);
        db.close();
        return newRowId;
    }
    public ArrayList<Direction> getAllHistory(){


        String selectQuery = "SELECT  * FROM " + SQLiteHelper.TABLE_NAME + " ORDER BY _ID DESC;";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<Direction> array  = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Direction curr = new Direction();

                curr.setID(cursor.getInt(0));
                curr.from = cursor.getString(1);
                curr.to = cursor.getString(2);
                curr.response = cursor.getString(3);
                switch (cursor.getString(4).charAt(0)){
                    case 'r':
                        curr.type = Direction.TYPE.R;
                        break;
                    case 'p':
                        curr.type = Direction.TYPE.P;
                        break;
                    case 'f':
                    case 'F':
                        curr.type = Direction.TYPE.F;
                        break;
                    case 'w':
                        curr.type = Direction.TYPE.W;
                        break;
                    default:
                        curr.type = Direction.TYPE.R;
                        break;
                }
                array.add(curr);
            } while (cursor.moveToNext());
        }
        db.close();
        return array;
    }
}
