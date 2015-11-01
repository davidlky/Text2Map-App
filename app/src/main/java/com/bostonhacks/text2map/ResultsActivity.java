package com.bostonhacks.text2map;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        setUpMapIfNeeded();
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
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        List<LatLng> points =  PolyUtil.decode("cknaGxo_qLiPvaB}Wvc@wOaw@~M`w@qEzgFd`Az`Lz{@duIbt@zsFdpAvtDt\\bpAiVrbD`l@jqGl_AveFlvBhcHneAjtF`iCthDrpBz}Fy@xdF|XpzKv|@lbGlfBlfLjrC|gO|kBhgFno@d|GrlBlgJed@fqC`Jt`EwfDbzHi|BpuEzNrrFvsA~~FwjAp`Elj@zwD`|Av|CiM~qFeoAxyE|s@zxOkp@jkHvXpwBjgAnfCxFphFjnAfkDm]nrHacAztGkDr~Eaa@tzDiv@~yE}IfeDurCfiDuoE~vGmr@hoCfSnkDqPh{C_l@bnCsV`rEggAnuA{zDfsEq|@hiEdNloSsmAhpJkwBzaAsrAzyDulBflEag@hcC{{AlvAww@dyAfJjmAaj@r~@uyA|iEidA|zAq`Bla@am@n_@yw@hdBkdAxwDiqAnsAyc@loBKzjH{oAfaGa}BvcKg}Eyc@{dBzh@wpCeXgwF__@ohBbfEaxD`oE_|BtAi|@rjAqnF``KaoCrrHerA~xFilBts@utBBelB_b@wgBlsBo}BtwCg`CriFk}AphHenCnoRlLphKokA`vGob@ryErnDnnIxyBh}CzYbuBtExgIiqAd_G_EfrB{j@t}Ak_Bf`A_r@`s@_xAjl@cnDz|C{v@|{C|EpyF_jDnjOrh@fvDz@hdDl_@vwCqYdfCq`@j|HkSdtDiGhzCm_Av`DuaBveByoA|zAe_E|~J_aCpoI}tA~kHmrAbiFfz@nuGxQ`bPufAhlMvfA`mRxKl`HxwAvrHb]tpF~pAxrH}`@~rS~GvwVjA``QlBh}HtChxDev@frB_xA`kE{NboCxmAfkJl_BzaEzAbmGnzAveX|gDpgVbhHziSj|BraNr_Ax~DxRdtDbu@roL`OhjFDfkHmuAvxJkZ|{ImN`_KadAzzUs{@`oOow@tiGmu@pdG{sBx}G_kA`aG}Hd_Id_@tcQsFhdOjqAdaHItbf@heBhhKgn@bfDv[tuYhh@fpXdhC|tJjoBzwLpdC`pSwSdwVoNn{AspAnRixAd|BkhC||D~Ml_BfrAblN{mA|dDsiAvaDwjDv}B{rFzVceCmv@exDtdFkbBlZac@|bAk]pqCnhAllFmn@tkHwxAheHwu@zoI{[r~Umh@|vHqZ~hVmxI~kk@o}G~yEq`CbhAemArqBo|@f`@cg@up@c~B{sD_kE{}EuxGysHs}DykEolCkE_zA_o@o{F{uG{dBskBccG}zGwq@ujAyn@iaG{vAceF`O_fEqk@_oIuNsZw~@n]");
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
    }
}
