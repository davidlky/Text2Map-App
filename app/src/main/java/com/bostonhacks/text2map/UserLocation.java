package com.bostonhacks.text2map;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by David Liu on 11/1/2015.
 */
public class UserLocation implements Serializable {
    String lat,lon,current_time;
    long id;

    public void save(Context c){
        DatabaseIO databaseIO = new DatabaseIO(c);
        id = databaseIO.recordHistory(this);
    }
}
