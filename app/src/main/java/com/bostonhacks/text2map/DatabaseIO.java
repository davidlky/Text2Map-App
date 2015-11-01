package com.bostonhacks.text2map;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                SQLiteHelper.TABLE_NAME,
                "null",
                values);
        return newRowId;
    }

    public boolean updateTable(Direction direction){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.columns[3], direction.response);

        // Which row to update, based on the ID
        String selection = SQLiteHelper.columns[0] + " = ";
        String[] selectionArgs = { String.valueOf(direction.getID()) };

        int count = db.update(
                SQLiteHelper.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return (count > 0);
    }
}
