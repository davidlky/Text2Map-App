package com.bostonhacks.text2map;

import android.content.Context;
import android.util.Log;

/**
 * Created by David Liu on 10/31/2015.
 */
public class Direction {
    private long id=-1;
    String to;
    String from;
    String response;
    private boolean recorded = false;

    void record(Context c){
        DatabaseIO io = new DatabaseIO(c);
        if(recorded){
            if (!io.updateTable(this)){
                Log.i("Store to DB","Not Stroing");
            }
        }else{
            id = io.writeToTable(this);
        }
    }

    public long getID(){
        return id;
    }
}
