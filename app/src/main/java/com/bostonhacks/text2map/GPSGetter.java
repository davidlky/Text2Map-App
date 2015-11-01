package com.bostonhacks.text2map;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by David Liu on 11/1/2015.
 */
public class GPSGetter implements LocationListener{
    Context context;
    TextView textView;
    GPSGetter(Context c, TextView tvChange){
        context = c;
        textView  = tvChange;
        LocationManager locationManager = (LocationManager) c.getSystemService(c.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            turnGPSOn();
        }

    }

    public UserLocation getLocation(){
        LocationManager locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            turnGPSOn();
        }

        // Get the location manager
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Double lat,lon;
        try {
            if (location==null){
                return null;
            }
            lat = location.getLatitude ();
            lon = location.getLongitude ();

            UserLocation userLocation = new UserLocation();
            userLocation.lat = Double.toString(lat);
            userLocation.lon = Double.toString(lon);
            long time = System.currentTimeMillis();
            userLocation.current_time = Long.toString(time);
            userLocation.save(context);
            return userLocation;
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }

    }
    public void turnGPSOn()
    {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }
    // automatic turn off the gps
    public void turnGPSOff()
    {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            this.context.sendBroadcast(poke);
        }
    }
    public String myLocation;

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        myLocation = "Latitude = " + location.getLatitude() + " Longitude = " + location.getLongitude();

        turnGPSOff();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
