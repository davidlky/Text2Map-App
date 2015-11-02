package com.bostonhacks.text2map.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bostonhacks.text2map.Direction;
import com.bostonhacks.text2map.R;

import java.util.ArrayList;

/**
 * Created by David Liu on 11/1/2015.
 */
public class HistoryListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Direction> directions;
    public HistoryListAdapter(Context c, ArrayList<Direction> directions){
        context = c;
        this.directions = directions;
    }
    @Override
    public int getCount() {
        return directions.size();
    }

    @Override
    public Object getItem(int i) {
        return directions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return directions.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.history_list_item, viewGroup, false);
        }
        Direction d = directions.get(i);
        String type = "";
        switch (d.type){
            case R:
                type = "Route";
                ((ImageView)view.findViewById(R.id.imageView6)).setImageDrawable(context.getDrawable(R.drawable.ic_directions_white_36dp));
                break;
            case P:
                type = "Public Transit";
                ((ImageView)view.findViewById(R.id.imageView6)).setImageDrawable(context.getDrawable(R.drawable.ic_directions_bus_white_36dp));
                break;
            case W:
                ((ImageView)view.findViewById(R.id.imageView6)).setImageDrawable(context.getDrawable(R.drawable.ic_wifi_tethering_white_36dp));
                type = "Wifi Locations";
                break;
            case F:
                ((ImageView)view.findViewById(R.id.imageView6)).setImageDrawable(context.getDrawable(R.drawable.ic_favorite_white_36dp));
                type = "Resturants";
                break;
        }
        ((TextView)view.findViewById(R.id.textView9)).setText(type);
        ((TextView)view.findViewById(R.id.textView10)).setText(d.from + " to " + d.to);

        return view;
    }
}
