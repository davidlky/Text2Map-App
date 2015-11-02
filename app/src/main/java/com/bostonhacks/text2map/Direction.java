package com.bostonhacks.text2map;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by David Liu on 10/31/2015.
 */
public class Direction implements Serializable {
    private long id=-1;
    public String to;
    public String from;
    String response="";
    public TYPE type;

    public enum TYPE{
        R,P,W,F
    }

    void record(Context c){
        DatabaseIO io = new DatabaseIO(c);
        if(!response.equals("")){
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

    public void setID(long id){
        this.id = id;
    }

    public boolean query(Context c){
        DatabaseIO io = new DatabaseIO(c);
        String temp = io.queryBy((int)id);
        if(temp.length()>0){
            response = temp;
            return true;
        }
        return false;
    }
}
