package com.bostonhacks.text2map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    Direction direction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Search Results");

        direction = (Direction) getIntent().getSerializableExtra("Direction");
        sendLongSMS();

        Thread thread = new Thread(){
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()){
                try {
                    synchronized (this) {
                        wait(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(direction.query(getBaseContext())){
                                    setUpMapIfNeeded(direction.response);
                                    Thread.currentThread().interrupt();
                                }
                            }
                        });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            };
        };
        thread.start();
    }

    public void sendLongSMS() {
        String message ="";
        switch (direction.type){
            case R:
                message+="r ";
                message += direction.getID()+"\n"+direction.from+"\n"+direction.to;
                break;
            case P:
                message+="p ";
                break;
            case W:
                message+="w ";
                break;
            case F:
                message+="f ";
                message += direction.getID()+"\n"+direction.from;
                break;
        }
        String phoneNumber = getString(R.string.twilio_number);

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(message);
        smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * setUpMapIfNeeded
     *
     * @param value
     */
    private void setUpMapIfNeeded(String value) {
        ResultsActivityFragment fragment = (ResultsActivityFragment) getFragmentManager().findFragmentById(R.id.fragment_list);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {//choose what to set up as
                if(value.equals("no routes found")){
                    ((TextView)fragment.inflated.findViewById(R.id.status_text)).setText("No Routes Were Found");
                }else {
                    switch (direction.type){
                        case R:
                            ((TextView)fragment.inflated.findViewById(R.id.status_text)).setText("Route was found");
                            setUpMapPolyline(value);
                            break;
                        case P:
                            break;
                        case W:
                            break;
                        case F:
                            ((TextView)fragment.inflated.findViewById(R.id.status_text)).setText("Route was found");
                            setUpMapPinpoints(value);
                            break;
                    }
                }
                ((TextView)fragment.inflated.findViewById(R.id.search_strings_display_text)).setText(direction.from + " to " + direction.to);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMapPolyline(String value) {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        List<LatLng> points =  PolyUtil.decode(value);
        PolylineOptions polyline = new PolylineOptions();
        for (LatLng point:points) {
            polyline.add(point);
        }
        polyline.color(getResources().getColor(R.color.primary));
        mMap.addPolyline(polyline);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point : points) {
            builder.include(point);
        }

        final LatLngBounds bounds = builder.build();
        int padding = 20; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
                padding);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
            }
        });
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


        //public class WaitForFile
    }
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            double lat = location.getLatitude ();
            double lon = location.getLongitude ();

            UserLocation userLocation = new UserLocation();
            userLocation.lat = Double.toString(lat);
            userLocation.lon = Double.toString(lon);
            long time = System.currentTimeMillis();
            userLocation.current_time = Long.toString(time);
            userLocation.save(getBaseContext());
        }
    };

    public void setUpMapPinpoints(String upMapPinpoints) {
        String[] lines = upMapPinpoints.split("\n");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (String line :lines){
            String[] sub = line.split(" ",3);
            LatLng loc = new LatLng(Double.parseDouble(sub[0]), Double.parseDouble(sub[1]));
            Marker m = mMap.addMarker(new MarkerOptions().position(loc).title(sub[2]));
            builder.include(m.getPosition());
        }


        final LatLngBounds bounds = builder.build();
        int padding = 20; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
                padding);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));
            }
        });
    }
}
