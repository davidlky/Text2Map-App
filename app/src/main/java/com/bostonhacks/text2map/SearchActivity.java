package com.bostonhacks.text2map;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    Direction direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViewById(R.id.card_view_2).setVisibility(View.GONE);
//        BitmapDrawable ob = decodeSampledBitmapFromResource(R.id.)
//        findViewById(R.id.linear_layout).setBackground(new Drawable() {
//        });
        direction = (Direction) getIntent().getSerializableExtra("Direction");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

    public void next(View view) {
        TextView tv  = (TextView) findViewById(R.id.text_question);
        switch(direction.type){
            case R:
                tv.setText("Where do you plan to go?");
                findViewById(R.id.card_view).setVisibility(View.GONE);
                findViewById(R.id.card_view_2).setVisibility(View.VISIBLE);
                direction.from = ((TextView) findViewById(R.id.editText)).getText().toString();
                break;
            case P:
                Toast.makeText(SearchActivity.this, "More to Come", Toast.LENGTH_SHORT).show();
                break;
            case W:
                break;
            case F:
                Intent i = new Intent(SearchActivity.this,ResultsActivity.class);
                direction.from = ((TextView) findViewById(R.id.editText)).getText().toString();
                direction.record(this);
                i.putExtra("Direction",direction);
                startActivity(i);
                break;
        }

    }

    public void gotoMaps(View view) {
        Intent i = new Intent(SearchActivity.this,ResultsActivity.class);
        direction.to = ((TextView) findViewById(R.id.editText2)).getText().toString();
        direction.record(this);
        i.putExtra("Direction",direction);
        startActivity(i);
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    @Override
    public void onBackPressed() {
        if(findViewById(R.id.card_view).getVisibility()==View.GONE){
            TextView tv  = (TextView) findViewById(R.id.text_question);
            tv.setText("Where are you now?");
            findViewById(R.id.card_view_2).setVisibility(View.GONE);
            findViewById(R.id.card_view).setVisibility(View.VISIBLE);
        }else{
            super.onBackPressed();
        }
    }

    public void setGPS(View view) {
        GPSGetter gps = new GPSGetter(this,(TextView) findViewById(R.id.editText));
        UserLocation userLocation = gps.getLocation();
        if (userLocation==null){
            Toast.makeText(this, "Location not yet found!", Toast.LENGTH_SHORT).show();
        }else {
            ((TextView) findViewById(R.id.editText)).setText(userLocation.lat + "," + userLocation.lon);
        }
    }
}
